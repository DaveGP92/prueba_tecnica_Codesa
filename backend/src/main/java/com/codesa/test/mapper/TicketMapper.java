package com.codesa.test.mapper;

import com.codesa.test.dto.TicketRequest;
import com.codesa.test.dto.TicketResponse;
import com.codesa.test.model.entity.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketMapper {

    public TicketResponse toResponse(Ticket entity) {

        TicketResponse response = new TicketResponse();

        response.setId(entity.getId());

        if (entity.getSchedule() != null) {
            response.setScheduleId(entity.getSchedule().getId());
        }

        if (entity.getUser() != null) {
            response.setUserId(entity.getUser().getId());
            response.setUserName(entity.getUser().getUserName());
        }

        response.setCustomerName(entity.getCustomerName());
        response.setCustomerEmail(entity.getCustomerEmail());
        response.setCustomerPhone(entity.getCustomerPhone());
        response.setQuantity(entity.getQuantity());
        response.setTotalPrice(entity.getTotalPrice());
        response.setActive(entity.getActive());
        response.setCreatedAt(entity.getCreatedAt());

        return response;
    }

    public Ticket toEntity(TicketRequest request) {

        Ticket ticket = new Ticket();

        ticket.setQuantity(request.getQuantity());
        ticket.setTotalPrice(request.getTotalPrice());

        return ticket;
    }

}
