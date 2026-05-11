package com.vn.library_service.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowSummaryResponse {
    private String id;
    private String readerId;
    private String readerName;
    private String readerCode;
    private Integer totalBooks;
    private LocalDate nearestDueDate;
}
