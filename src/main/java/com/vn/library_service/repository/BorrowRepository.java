package com.vn.library_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vn.library_service.modal.Borrow;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, String> {

    // Mỗi reader chỉ có 1 phiếu mượn
    Optional<Borrow> findByReader_Id(String readerId);

    Optional<Borrow> findByReader_Code(String readerCode);
}
