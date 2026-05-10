package com.vn.library_service.service;

import java.util.List;

import com.vn.library_service.dto.request.BookRequest;
import com.vn.library_service.dto.response.BookDetailResponse;
import com.vn.library_service.dto.response.BookSummaryResponse;
import com.vn.library_service.modal.Book;

public interface BookService {

    List<BookSummaryResponse> getAllBooks();

    BookDetailResponse getBookById(String id);

    BookDetailResponse createNewBook(BookRequest request);

    BookDetailResponse updateBookById(String id, BookRequest request);

    void deleteBookById(String id);

}
