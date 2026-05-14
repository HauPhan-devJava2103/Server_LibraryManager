package com.vn.library_service.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardReportResponse {

    private long totalBooksInStock;
    private long totalBooksBorrowed;
    private long totalReaders;

    private List<TopBorrowedBookResponse> mostBorrowedBooks;
    private List<LowStockBookResponse> lowStockBooks;
    private List<OverdueReaderResponse> overdueReaders;

}
