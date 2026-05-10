package com.vn.library_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginationMeta {
    private int totalItems;
    private int totalPages;
    private int currentPage;
    private int pageSize;
}
