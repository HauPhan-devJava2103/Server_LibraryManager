package com.vn.library_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vn.library_service.dto.request.ReaderRequest;
import com.vn.library_service.dto.response.ReaderDetailResponse;
import com.vn.library_service.dto.response.ReaderSummaryResponse;
import com.vn.library_service.modal.Reader;

@Mapper(componentModel = "spring")
public interface ReaderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Reader toReader(ReaderRequest readerRequest);

    ReaderDetailResponse toReaderDetailResponse(Reader reader);

    ReaderSummaryResponse toReaderSummaryResponse(Reader reader);

}
