package com.vn.library_service.dto.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDetailResponse {
    private String id;
    private String title;
    private String author;
    private Integer publishedYear;
    private String description;

    private Date createdAt;
    private Date updatedAt;
}
