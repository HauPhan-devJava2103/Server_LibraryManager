package com.vn.library_service.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vn.library_service.dto.request.ReaderRequest;
import com.vn.library_service.dto.response.ReaderDetailResponse;
import com.vn.library_service.dto.response.ReaderSummaryResponse;
import com.vn.library_service.exception.BadRequestException;
import com.vn.library_service.exception.ResourceNotFoundException;
import com.vn.library_service.mapper.ReaderMapper;
import com.vn.library_service.modal.Reader;
import com.vn.library_service.repository.ReaderRepository;
import com.vn.library_service.service.ReaderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReaderServiceImpl implements ReaderService {

    private final ReaderRepository readerRepository;
    private final ReaderMapper readerMapper;

    // Lấy toàn bộ danh sách bạn đọc
    @Override
    public List<ReaderSummaryResponse> getAllReaders() {
        List<Reader> listReaders = readerRepository.findAll();
        return listReaders.stream()
                .map(readerMapper::toReaderSummaryResponse)
                .toList();
    }

    // Lấy thông tin chi tiết của bạn đọc theo id
    @Override
    public ReaderDetailResponse getReaderById(String id) {
        Reader reader = findReaderById(id);
        return readerMapper.toReaderDetailResponse(reader);
    }

    // Tạo mới bạn đọc và kiểm tra trùng số điện thoại
    @Override
    public ReaderDetailResponse createNewReader(ReaderRequest request) {
        if (readerRepository.existsByPhone(request.getPhone())) {
            throw new BadRequestException("Reader already exists with phone: " + request.getPhone());
        }
        Reader reader = readerMapper.toReader(request);

        String generatedCode = String.valueOf(System.currentTimeMillis() % 1000000);
        reader.setCode(generatedCode);

        reader = readerRepository.save(reader);
        return readerMapper.toReaderDetailResponse(reader);
    }

    // Cập nhật thông tin bạn đọc và kiểm tra số điện thoại
    @Override
    public ReaderDetailResponse updateReaderById(String id, ReaderRequest request) {
        Reader reader = findReaderById(id);

        if (!reader.getPhone().equals(request.getPhone()) && readerRepository.existsByPhone(request.getPhone())) {
            throw new BadRequestException("Reader already exists with phone: " + request.getPhone());
        }

        reader.setName(request.getName());
        reader.setPhone(request.getPhone());

        reader = readerRepository.save(reader);
        return readerMapper.toReaderDetailResponse(reader);
    }

    // Xóa bạn đọc theo id
    @Override
    public void deleteReaderById(String id) {
        Reader reader = findReaderById(id);
        readerRepository.delete(reader);
    }

    // HELPERS METHOD
    // Tìm bạn đọc theo id, ném lỗi nếu không tồn tại
    private Reader findReaderById(String id) {
        return readerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reader not found with id " + id));
    }

}
