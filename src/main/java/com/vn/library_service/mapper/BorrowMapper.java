package com.vn.library_service.mapper;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.vn.library_service.dto.response.BorrowDetailResponse;
import com.vn.library_service.dto.response.BorrowItemResponse;
import com.vn.library_service.dto.response.BorrowSummaryResponse;
import com.vn.library_service.modal.Borrow;
import com.vn.library_service.modal.BorrowItem;
import com.vn.library_service.util.EBorrow;

@Mapper(componentModel = "spring")
public interface BorrowMapper {

    @Mapping(source = "reader.id", target = "readerId")
    @Mapping(source = "reader.name", target = "readerName")
    @Mapping(source = "reader.code", target = "readerCode")
    @Mapping(source = "items", target = "totalBooks", qualifiedByName = "calcTotalBooks")
    @Mapping(source = "items", target = "nearestDueDate", qualifiedByName = "calcNearestDueDate")
    BorrowSummaryResponse toBorrowSummaryResponse(Borrow borrow);

    @Mapping(source = "reader.id", target = "readerId")
    @Mapping(source = "reader.name", target = "readerName")
    @Mapping(source = "reader.code", target = "readerCode")
    @Mapping(source = "reader.phone", target = "readerPhone")
    @Mapping(source = "items", target = "totalBooks", qualifiedByName = "calcTotalBooks")
    @Mapping(source = "items", target = "nearestDueDate", qualifiedByName = "calcNearestDueDate")
    @Mapping(source = "items", target = "items", qualifiedByName = "filterActiveItems")
    BorrowDetailResponse toBorrowDetailResponse(Borrow borrow);

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "bookTitle")
    BorrowItemResponse toBorrowItemResponse(BorrowItem item);

    // HELPERS METHOD

    // 1. Tính tổng sách đang mượn
    @Named("calcTotalBooks")
    default Integer calcTotalBooks(List<BorrowItem> items) {

        if (items == null)
            return 0;
        return items.stream()
                .filter(item -> item.getStatus() == EBorrow.BORROWING || item.getStatus() == EBorrow.OVERDUE)
                .mapToInt(BorrowItem::getQuantity)
                .sum();
    }

    // 2. Tính ngày đến hạn gần nhất
    @Named("calcNearestDueDate")
    default LocalDate calcNearestDueDate(List<BorrowItem> items) {
        if (items == null)
            return null;

        return items.stream()
                .filter(item -> item.getStatus() == EBorrow.BORROWING || item.getStatus() == EBorrow.OVERDUE)
                .map(BorrowItem::getDueDate)
                .min(Comparator.naturalOrder())
                .orElse(null);

    }

    // 3. Loại bỏ RETURNED
    @Named("filterActiveItems")
    default List<BorrowItemResponse> filterActiveItems(List<BorrowItem> items) {
        if (items == null)
            return List.of();
        return items.stream()
                .filter(item -> item.getStatus() == EBorrow.BORROWING || item.getStatus() == EBorrow.OVERDUE)
                .map(this::toBorrowItemResponse)
                .toList();
    }

}
