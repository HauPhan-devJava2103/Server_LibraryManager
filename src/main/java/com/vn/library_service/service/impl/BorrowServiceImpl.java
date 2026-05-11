package com.vn.library_service.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vn.library_service.dto.request.BorrowItemRequest;
import com.vn.library_service.dto.request.CreateBorrowRequest;
import com.vn.library_service.dto.request.ReturnBookRequest;
import com.vn.library_service.dto.response.BorrowDetailResponse;
import com.vn.library_service.dto.response.BorrowItemResponse;
import com.vn.library_service.dto.response.BorrowSummaryResponse;
import com.vn.library_service.exception.BadRequestException;
import com.vn.library_service.exception.ResourceNotFoundException;
import com.vn.library_service.mapper.BorrowMapper;
import com.vn.library_service.modal.Book;
import com.vn.library_service.modal.Borrow;
import com.vn.library_service.modal.BorrowItem;
import com.vn.library_service.modal.Reader;
import com.vn.library_service.repository.BookRepository;
import com.vn.library_service.repository.BorrowRepository;
import com.vn.library_service.repository.ReaderRepository;
import com.vn.library_service.service.BorrowService;
import com.vn.library_service.util.EBorrow;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BorrowServiceImpl implements BorrowService {

    private final BorrowRepository borrowRepository;
    private final ReaderRepository readerRepository;
    private final BookRepository bookRepository;
    private final BorrowMapper borrowMapper;

    @Override
    public List<BorrowSummaryResponse> getAllBorrows() {
        List<Borrow> borrows = borrowRepository.findAll();
        return borrows.stream().map(borrowMapper::toBorrowSummaryResponse).toList();
    }

    @Override
    public BorrowDetailResponse getBorrowDetailById(String id) {
        Borrow borrow = borrowRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phiếu mượn với id: " + id));
        return borrowMapper.toBorrowDetailResponse(borrow);
    }

    @Override
    public BorrowDetailResponse getBorrowByReaderCode(String readerCode) {
        Borrow borrow = borrowRepository.findByReader_Code(readerCode)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy phiếu mượn với mã độc giả: " + readerCode));
        return borrowMapper.toBorrowDetailResponse(borrow);
    }

    @Transactional
    @Override
    public BorrowDetailResponse borrowBooks(String readerId, CreateBorrowRequest request) {

        Reader reader = readerRepository.findById(readerId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy độc giả với id: " + readerId));

        Borrow borrow = borrowRepository.findByReader_Id(readerId)
                .orElseGet(() -> {
                    Borrow newBorrow = new Borrow();
                    newBorrow.setReader(reader);
                    return borrowRepository.save(newBorrow);
                });

        LocalDate borrowDate = LocalDate.now();
        LocalDate dueDate = borrowDate.plusMonths(1); // hạn trả 1 tháng sau khi mượn

        for (BorrowItemRequest item : request.getItems()) {
            // Check book
            Book book = bookRepository.findById(item.getBookId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Không tìm thấy sách với ID: " + item.getBookId()));

            // Check stock
            if (item.getQuantity() > book.getStock()) {
                throw new BadRequestException(
                        "Sách '" + book.getTitle() + "' không đủ số lượng. " +
                                "Còn lại: " + book.getStock() + ", yêu cầu: " + item.getQuantity());
            }

            // Trừ stock
            book.setStock(book.getStock() - item.getQuantity());
            bookRepository.save(book);

            // Tạo BorrowItem
            BorrowItem borrowItem = new BorrowItem();
            borrowItem.setBorrow(borrow);
            borrowItem.setBook(book);
            borrowItem.setQuantity(item.getQuantity());
            borrowItem.setBorrowDate(borrowDate);
            borrowItem.setDueDate(dueDate);
            borrowItem.setStatus(EBorrow.BORROWING);

            borrow.getItems().add(borrowItem);
        }

        Borrow saved = borrowRepository.save(borrow);

        // Tổng sách mới mượn vào
        Integer totalBooks = saved.getItems().stream()
                .filter(item -> item.getStatus() == EBorrow.BORROWING || item.getStatus() == EBorrow.OVERDUE)
                .mapToInt(BorrowItem::getQuantity)
                .sum();

        BorrowDetailResponse response = borrowMapper.toBorrowDetailResponse(saved);
        // 3. Ghi đè danh sách items đã lọc
        response.setTotalBooks(totalBooks);

        return response;
    }

    @Transactional
    @Override
    public BorrowDetailResponse returnBooks(String borrowId, ReturnBookRequest request) {
        Borrow borrow = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy phiếu mượn với ID: " + borrowId));

        for (BorrowItemRequest req : request.getItems()) {
            BorrowItem item = borrow.getItems().stream()
                    .filter(borrowItem -> borrowItem.getBook().getId().equals(req.getBookId()))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Không tìm thấy sách trong phiếu mượn: " + req.getBookId()));
            int bookQuantity = item.getQuantity();
            int returnQuantity = req.getQuantity();
            if (returnQuantity > bookQuantity) {
                throw new BadRequestException(
                        "Số lượng trả (" + returnQuantity + ") vượt quá số lượng đang mượn (" + bookQuantity + ")");
            }
            item.setQuantity(bookQuantity - returnQuantity);
            if (item.getQuantity() == 0) {
                item.setStatus(EBorrow.RETURNED);
            }
            // Add stock back
            Book book = item.getBook();
            book.setStock(book.getStock() + returnQuantity);

            // Save Book
            bookRepository.save(book);
        }
        Borrow saved = borrowRepository.save(borrow);

        List<BorrowItem> remainBorrowItems = saved.getItems().stream()
                .filter(item -> item.getStatus() == EBorrow.BORROWING || item.getStatus() == EBorrow.OVERDUE)
                .toList();

        BorrowDetailResponse response = borrowMapper.toBorrowDetailResponse(saved);

        if (remainBorrowItems.isEmpty()) {
            response.setItems(null);
        } else {

            List<BorrowItemResponse> itemResponses = remainBorrowItems.stream()
                    .map(borrowMapper::toBorrowItemResponse)
                    .toList();

            Integer totalBooks = itemResponses.stream()
                    .filter(item -> item.getStatus() == EBorrow.BORROWING || item.getStatus() == EBorrow.OVERDUE)
                    .mapToInt(BorrowItemResponse::getQuantity)
                    .sum();

            response.setTotalBooks(totalBooks);
        }

        return response;
    }

}
