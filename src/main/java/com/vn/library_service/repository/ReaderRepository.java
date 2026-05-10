package com.vn.library_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vn.library_service.modal.Reader;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, String> {
    Boolean existsByPhone(String phone);
}
