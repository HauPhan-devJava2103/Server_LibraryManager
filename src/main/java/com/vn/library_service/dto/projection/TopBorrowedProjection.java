package com.vn.library_service.dto.projection;

public interface TopBorrowedProjection {
    String getId();

    String getTitle();

    Long getTotalBorrowed();
}
