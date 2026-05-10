package com.vn.library_service.service;

import java.util.List;

import com.vn.library_service.dto.request.ReaderRequest;
import com.vn.library_service.dto.response.ReaderDetailResponse;
import com.vn.library_service.dto.response.ReaderSummaryResponse;

public interface ReaderService {

    List<ReaderSummaryResponse> getAllReaders();

    ReaderDetailResponse getReaderById(String id);

    ReaderDetailResponse createNewReader(ReaderRequest request);

    ReaderDetailResponse updateReaderById(String id, ReaderRequest request);

    void deleteReaderById(String id);

}
