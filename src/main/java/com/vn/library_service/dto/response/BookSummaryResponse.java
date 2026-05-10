package com.vn.library_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookSummaryResponse {
    private String id;
    private String title;
    private String author;
    private Integer publishedYear;
    private Integer stock;
}
