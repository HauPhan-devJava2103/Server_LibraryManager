package com.vn.library_service.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vn.library_service.dto.request.BookRequest;
import com.vn.library_service.dto.response.BookDetailResponse;
import com.vn.library_service.dto.response.BookSummaryResponse;
import com.vn.library_service.exception.BadRequestException;
import com.vn.library_service.exception.ResourceNotFoundException;
import com.vn.library_service.mapper.BookMapper;
import com.vn.library_service.modal.Book;
import com.vn.library_service.repository.BookRepository;
import com.vn.library_service.service.BookService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public List<BookSummaryResponse> getAllBooks() {
        List<Book> listBooks = bookRepository.findAll();
        return listBooks.stream()
                .map(bookMapper::toBookSummaryResponse)
                .toList();
    }

    @Override
    public BookDetailResponse getBookById(String id) {
        Book book = findBookById(id);
        return bookMapper.toBookDetailResponse(book);
    }

    @Override
    public BookDetailResponse createNewBook(BookRequest request) {
        if (bookRepository.existsByTitle(request.getTitle())) {
            throw new BadRequestException("Book already exists with title: " + request.getTitle());
        }
        Book book = bookMapper.toBook(request);

        book = bookRepository.save(book);
        return bookMapper.toBookDetailResponse(book);
    }

    @Override
    public BookDetailResponse updateBookById(String id, BookRequest request) {
        Book book = findBookById(id);

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPublishedYear(request.getPublishedYear());
        book.setDescription(request.getDescription());
        book.setStock(request.getStock());

        book = bookRepository.save(book);
        return bookMapper.toBookDetailResponse(book);
    }

    @Override
    public void deleteBookById(String id) {
        Book book = findBookById(id);
        bookRepository.delete(book);
    }

    // HELPERS METHOD
    private Book findBookById(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));
    }

}
