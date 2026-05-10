package com.vn.library_service.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReaderDetailResponse {
    private String id;
    private String name;
    private String phone;
    private String code;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
