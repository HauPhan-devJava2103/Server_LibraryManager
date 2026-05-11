package com.vn.library_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vn.library_service.dto.ApiResponse;
import com.vn.library_service.dto.request.CreateBorrowRequest;
import com.vn.library_service.dto.request.ReturnBookRequest;
import com.vn.library_service.dto.response.BorrowDetailResponse;
import com.vn.library_service.dto.response.BorrowSummaryResponse;
import com.vn.library_service.service.impl.BorrowServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/borrows")
@RequiredArgsConstructor
public class BorrowController {

    private final BorrowServiceImpl borrowService;

    // Get All Borrows
    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllBorrows() {

        List<BorrowSummaryResponse> data = borrowService.getAllBorrows();
        String message = data.isEmpty()
                ? "Không có phiếu mượn nào."
                : "Lấy danh sách phiếu mượn thành công.";

        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message(message)
                .data(data)
                .build());

    }

    // Get Borrow By Id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getBorrowById(@PathVariable String id) {

        BorrowDetailResponse data = borrowService.getBorrowDetailById(id);

        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Lấy chi tiết phiếu mượn thành công.")
                .data(data)
                .build());
    }

    // Get Borrow By Reader Code
    @GetMapping("/reader/{readerCode}")
    public ResponseEntity<ApiResponse> getBorrowByReaderCode(@PathVariable String readerCode) {

        BorrowDetailResponse data = borrowService.getBorrowByReaderCode(readerCode);

        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Tìm thông tin mượn sách thành công.")
                .data(data)
                .build());
    }

    // Create Borrow For Reader
    @PostMapping("/{readerId}/borrow")
    public ResponseEntity<ApiResponse> borrowBook(@PathVariable String readerId,
            @Valid @RequestBody CreateBorrowRequest request) {

        BorrowDetailResponse data = borrowService.borrowBooks(readerId, request);

        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Mượn thành công " + data.getTotalBooks() + " cuốn sách.")
                .data(data)
                .build());
    }

    // Return Book
    @PostMapping("/{borrowId}/return")
    public ResponseEntity<ApiResponse> returnBooks(@PathVariable String borrowId,
            @Valid @RequestBody ReturnBookRequest request) {

        BorrowDetailResponse data = borrowService.returnBooks(borrowId, request);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Trả sách thành công.")
                .data(data)
                .build());
    }

}
