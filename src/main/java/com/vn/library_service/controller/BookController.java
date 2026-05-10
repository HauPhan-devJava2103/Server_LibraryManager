package com.vn.library_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vn.library_service.dto.ApiResponse;
import com.vn.library_service.dto.request.BookRequest;
import com.vn.library_service.dto.response.BookDetailResponse;
import com.vn.library_service.dto.response.BookSummaryResponse;
import com.vn.library_service.service.impl.BookServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookServiceImpl bookService;

    // Get All Books
    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllBooks() {
        List<BookSummaryResponse> data = bookService.getAllBooks();
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Lấy danh sách sách thành công.")
                .data(data)
                .build());

    }

    // Get Book By Id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getBookById(@PathVariable String id) {
        BookDetailResponse data = bookService.getBookById(id);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Lấy thông tin sách thành công.")
                .data(data)
                .build());

    }

    // Create New Book
    @PostMapping("")
    public ResponseEntity<ApiResponse> createNewBook(@Valid @RequestBody BookRequest request) {
        BookDetailResponse data = bookService.createNewBook(request);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Thêm sách thành công.")
                .data(data)
                .build());
    }

    // Update Book By Id
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateBookById(@PathVariable String id, @RequestBody BookRequest request) {
        BookDetailResponse data = bookService.updateBookById(id, request);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Cập nhật thông tin sách thành công.")
                .data(data)
                .build());
    }

    // Delete Book By Id
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteBookById(@PathVariable String id) {
        bookService.deleteBookById(id);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Xóa sách thành công.")
                .build());
    }
}
