package com.codesa.test.dto;

import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
public class ScheduleResponse {

    private Long id;

    private Long playId;

    private String playTitle;

    private Date dateTime;

    private String room;

    private String totalSeats;

    private String availableSeats;

    private String basePrice;

    private Boolean active;

    private LocalDateTime createdAt;

}
