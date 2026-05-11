package com.vn.library_service.dto.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateBorrowRequest {

    @NotEmpty(message = "Items must not be empty")
    @Valid
    private List<BorrowItemRequest> items;
}
