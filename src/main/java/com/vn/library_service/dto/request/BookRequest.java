package com.vn.library_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @Min(value = 1000, message = "Published Year Must Be Greater Than 1000")
    private Integer publishedYear;

    private String description;

    @Min(value = 0, message = "Stock must be greater than or equal to 0")
    private Integer stock;
}
