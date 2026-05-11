package com.vn.library_service.dto.response;

import java.time.LocalDate;

import com.vn.library_service.util.EBorrow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BorrowItemResponse {
    private String id;
    private String bookId;
    private String bookTitle;
    private Integer quantity;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private EBorrow status;
}
