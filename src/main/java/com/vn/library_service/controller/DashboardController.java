package com.vn.library_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vn.library_service.dto.ApiResponse;
import com.vn.library_service.dto.response.DashboardReportResponse;
import com.vn.library_service.service.impl.DashboardServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardServiceImpl dashboardService;

    @GetMapping("/reports/dashboard")
    public ResponseEntity<ApiResponse> getDashboardReport() {
        DashboardReportResponse data = dashboardService.getDashboardReport();
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Lấy dữ liệu báo cáo thành công")
                .data(data)
                .build());
    }

}
