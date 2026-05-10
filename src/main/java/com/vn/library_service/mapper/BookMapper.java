package com.vn.library_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vn.library_service.dto.request.BookRequest;
import com.vn.library_service.dto.response.BookDetailResponse;
import com.vn.library_service.dto.response.BookSummaryResponse;
import com.vn.library_service.modal.Book;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Book toBook(BookRequest bookRequest);

    BookDetailResponse toBookDetailResponse(Book book);

    BookSummaryResponse toBookSummaryResponse(Book book);

}
