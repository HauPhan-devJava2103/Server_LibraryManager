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
import com.vn.library_service.dto.request.ReaderRequest;
import com.vn.library_service.dto.response.ReaderDetailResponse;
import com.vn.library_service.dto.response.ReaderSummaryResponse;
import com.vn.library_service.service.impl.ReaderServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/readers")
@RequiredArgsConstructor
public class ReaderController {

    private final ReaderServiceImpl readerService;

    // Get All Readers
    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllReaders() {
        List<ReaderSummaryResponse> data = readerService.getAllReaders();
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Lấy danh sách bạn đọc thành công.")
                .data(data)
                .build());

    }

    // Get Reader By Id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getReaderById(@PathVariable String id) {
        ReaderDetailResponse data = readerService.getReaderById(id);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Lấy thông tin bạn đọc thành công.")
                .data(data)
                .build());

    }

    // Create New Reader
    @PostMapping("")
    public ResponseEntity<ApiResponse> createNewReader(@Valid @RequestBody ReaderRequest request) {
        ReaderDetailResponse data = readerService.createNewReader(request);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Thêm bạn đọc thành công.")
                .data(data)
                .build());
    }

    // Update Reader By Id
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateReaderById(@PathVariable String id, @RequestBody ReaderRequest request) {
        ReaderDetailResponse data = readerService.updateReaderById(id, request);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Cập nhật thông tin bạn đọc thành công.")
                .data(data)
                .build());
    }

    // Delete Reader By Id
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteReaderById(@PathVariable String id) {
        readerService.deleteReaderById(id);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Xóa bạn đọc thành công.")
                .build());
    }
}
