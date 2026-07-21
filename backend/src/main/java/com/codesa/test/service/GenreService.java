package com.codesa.test.service;

import com.codesa.test.dto.GenreResponse;
import com.codesa.test.dto.GenreRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GenreService {

    List<GenreResponse> findAll();
    Page<GenreResponse> findAllPaged(int page, int size);
    GenreResponse findById(Long id) throws Exception;
    GenreResponse create(GenreRequest request);
    GenreResponse update(Long id, GenreRequest request);
    void delete(Long id);
}
