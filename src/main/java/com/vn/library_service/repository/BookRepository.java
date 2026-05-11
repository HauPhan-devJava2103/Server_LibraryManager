package com.vn.library_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vn.library_service.modal.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    Boolean existsByTitle(String title);

}
