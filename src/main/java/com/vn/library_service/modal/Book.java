package com.vn.library_service.modal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "book")
public class Book extends AbstractEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "published_year")
    private Integer publishedYear;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "stock")
    private Integer stock;
    
}
