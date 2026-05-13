package com.vn.library_service.dto.projection;

public interface OverdueReaderProjection {
    String getBorrowId();

    String getReaderId();

    String getReaderName();

    String getReaderCode();

    Long getTotalOverdueBooks();
}
