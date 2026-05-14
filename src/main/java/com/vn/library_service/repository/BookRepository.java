package com.vn.library_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vn.library_service.dto.projection.LowStockProjection;
import com.vn.library_service.modal.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    Boolean existsByTitle(String title);

    @Query("SELECT COALESCE(SUM(b.stock),0) FROM Book b")
    Long totalBookInStock();

    @Query("SELECT b.id AS id, b.title AS title, b.stock AS stock "
            + "FROM Book b "
            + "WHERE b.stock <= 5 "
            + "ORDER BY b.stock ASC")
    List<LowStockProjection> lowStockBooks();

}
