package com.vn.library_service.service;

import java.util.List;

import com.vn.library_service.dto.request.CreateBorrowRequest;
import com.vn.library_service.dto.request.ReturnBookRequest;
import com.vn.library_service.dto.response.BorrowDetailResponse;
import com.vn.library_service.dto.response.BorrowSummaryResponse;

public interface BorrowService {

    List<BorrowSummaryResponse> getAllBorrows();

    BorrowDetailResponse getBorrowDetailById(String id);

    BorrowDetailResponse getBorrowByReaderCode(String readerCode);

    BorrowDetailResponse borrowBooks(String readerId, CreateBorrowRequest request);

    BorrowDetailResponse returnBooks(String borrowId, ReturnBookRequest request);

}
