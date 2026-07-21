package com.codesa.test.service;

import com.codesa.test.dto.ActiveScheduleResponse;
import com.codesa.test.dto.ScheduleRequest;
import com.codesa.test.dto.ScheduleResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ScheduleService {

    List<ScheduleResponse> findAll();
    List<ActiveScheduleResponse> findActiveSchedules();
    Page<ScheduleResponse> findAllPaged(int page, int size);
    ScheduleResponse findById(Long id) throws Exception;
    ScheduleResponse create(ScheduleRequest request);
    ScheduleResponse update(Long id, ScheduleRequest request);
    void delete(Long id);
}
