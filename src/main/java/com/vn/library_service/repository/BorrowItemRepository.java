package com.vn.library_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vn.library_service.dto.projection.OverdueReaderProjection;
import com.vn.library_service.dto.projection.TopBorrowedProjection;
import com.vn.library_service.modal.BorrowItem;

@Repository
public interface BorrowItemRepository extends JpaRepository<BorrowItem, String> {

    @Query("SELECT COALESCE(SUM(bi.quantity),0) FROM BorrowItem bi WHERE bi.status IN ('BORROWING', 'OVERDUE')")
    Long totalBooksBorrowed();

    // Top sách mượn nhiều
    @Query("SELECT bi.book.id AS id, bi.book.title AS title, SUM(bi.quantity) AS totalBorrowed"
            + " FROM BorrowItem bi WHERE bi.status IN ('BORROWING', 'OVERDUE') "
            + "GROUP BY bi.book.id, bi.book.title "
            + "ORDER BY SUM(bi.quantity) DESC ")
    List<TopBorrowedProjection> topMostBorrowedBooks();

    // Danh sách đọc giả quá hạn
    @Query("SELECT bi.borrow.id AS borrowId, bi.borrow.reader.id AS readerId, " +
            "bi.borrow.reader.name AS readerName, bi.borrow.reader.code AS readerCode, " +
            "COUNT(bi) AS totalOverdueBooks " +
            "FROM BorrowItem bi " +
            "WHERE bi.status = 'OVERDUE' " +
            "GROUP BY bi.borrow.id, bi.borrow.reader.id, " +
            "bi.borrow.reader.name, bi.borrow.reader.code")
    List<OverdueReaderProjection> findOverdueReaders();
}
