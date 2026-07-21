package com.codesa.test.service;

import com.codesa.test.dto.TicketPurchaseRequest;
import com.codesa.test.dto.TicketRequest;
import com.codesa.test.dto.TicketResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TicketService {

    List<TicketResponse> findAll();
    Page<TicketResponse> findAllPaged(int page, int size);
    TicketResponse findById(Long id) throws Exception;
    TicketResponse purchase(TicketPurchaseRequest request);
    TicketResponse create(TicketRequest request);
    TicketResponse update(Long id, TicketRequest request);
    void delete(Long id);
}
