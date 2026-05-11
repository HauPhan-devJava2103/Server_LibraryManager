package com.vn.library_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BorrowItemRequest {
    @NotNull(message = "Book ID is required")
    private String bookId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}
