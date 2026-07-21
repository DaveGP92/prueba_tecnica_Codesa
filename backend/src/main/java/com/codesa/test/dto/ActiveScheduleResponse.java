package com.codesa.test.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class ActiveScheduleResponse {

    private Long scheduleId;

    private Date dateTime;

    private String room;

    private String availableSeats;

    private String basePrice;

    private Long playId;

    private String playTitle;

    private String playDescription;

    private Long durationMinutes;

    private Long genreId;

    private String genreDescription;

}
