package com.codesa.test.service;

import com.codesa.test.dto.PlayRequest;
import com.codesa.test.dto.PlayResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PlayService {

    List<PlayResponse> findAll();
    Page<PlayResponse> findAllPaged(int page, int size);
    PlayResponse findById(Long id) throws Exception;
    PlayResponse create(PlayRequest request);
    PlayResponse update(Long id, PlayRequest request);
    void delete(Long id);
}
