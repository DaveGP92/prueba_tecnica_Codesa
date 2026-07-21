package com.codesa.test.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketResponse {

    private Long id;

    private Long scheduleId;

    private Long userId;

    private String userName;

    private String customerName;

    private String customerEmail;

    private String customerPhone;

    private Long quantity;

    private Float totalPrice;

    private Boolean active;

    private LocalDateTime createdAt;

}
