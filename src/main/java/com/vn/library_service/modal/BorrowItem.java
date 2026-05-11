package com.vn.library_service.modal;

import java.time.LocalDate;

import com.vn.library_service.util.EBorrow;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "borrow_item")
public class BorrowItem extends AbstractEntity {

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "borrow_date", nullable = false)
    private LocalDate borrowDate;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    // Trạng thái: BORROWING, RETURNED, OVERDUE
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EBorrow status = EBorrow.BORROWING;

    // RELATIONS

    // Thuộc phiếu mượn
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrow_id", nullable = false)
    private Borrow borrow;

    // Sách nào được mượn
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
}
