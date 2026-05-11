package com.vn.library_service.dto.response;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BorrowDetailResponse {
    private String id;
    private String readerId;
    private String readerName;
    private String readerCode;
    private String readerPhone;
    private Integer totalBooks;
    private LocalDate nearestDueDate;
    private List<BorrowItemResponse> items;
}