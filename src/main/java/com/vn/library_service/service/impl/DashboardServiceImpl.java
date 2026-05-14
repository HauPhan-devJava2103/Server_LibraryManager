package com.vn.library_service.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vn.library_service.dto.response.DashboardReportResponse;
import com.vn.library_service.dto.response.LowStockBookResponse;
import com.vn.library_service.dto.response.OverdueReaderResponse;
import com.vn.library_service.dto.response.TopBorrowedBookResponse;
import com.vn.library_service.repository.BookRepository;
import com.vn.library_service.repository.BorrowItemRepository;
import com.vn.library_service.repository.ReaderRepository;
import com.vn.library_service.service.DashboardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final BookRepository bookRepository;
    private final BorrowItemRepository borrowItemRepository;
    private final ReaderRepository readerRepository;

    @Override
    public DashboardReportResponse getDashboardReport() {
        // Thống kê
        long totalBooksInStock = bookRepository.totalBookInStock();
        long totalBooksBorrowed = borrowItemRepository.totalBooksBorrowed();
        long totalReaders = readerRepository.count();

        // Top 10 Sách mượn nhiều nhất
        List<TopBorrowedBookResponse> mostBorrowedBooks = borrowItemRepository
                .topMostBorrowedBooks()
                .stream()
                .limit(10)
                .map(row -> TopBorrowedBookResponse.builder()
                        .id(row.getId())
                        .title(row.getTitle())
                        .totalBorrowed(row.getTotalBorrowed())
                        .build())
                .toList();

        // Lấy sách sắp hết hạn trong kho
        List<LowStockBookResponse> lowStockBooks = bookRepository
                .lowStockBooks()
                .stream()
                .limit(10)
                .map(row -> LowStockBookResponse.builder()
                        .id(row.getId())
                        .title(row.getTitle())
                        .stock(row.getStock())
                        .build())
                .toList();

        // Danh sách đọc giả quá hạn
        List<OverdueReaderResponse> overdueReaders = borrowItemRepository.findOverdueReaders()
                .stream()
                .map(row -> OverdueReaderResponse.builder()
                        .borrowId(row.getBorrowId())
                        .readerId(row.getReaderId())
                        .readerName(row.getReaderName())
                        .readerCode(row.getReaderCode())
                        .totalOverdueBooks(row.getTotalOverdueBooks())
                        .build())
                .toList();

        return DashboardReportResponse.builder()
                .totalBooksInStock(totalBooksInStock)
                .totalBooksBorrowed(totalBooksBorrowed)
                .totalReaders(totalReaders)
                .mostBorrowedBooks(mostBorrowedBooks)
                .lowStockBooks(lowStockBooks)
                .overdueReaders(overdueReaders)
                .build();
    }

}
