package com.codesa.test.dto;

import lombok.Data;

import java.sql.Time;
import java.time.LocalDateTime;

@Data
public class PlayResponse {

    private Long id;

    private String title;

    private String description;

    private Long genreId;

    private String genreDescription;

    private Long durationMinutes;

    private Boolean active;

    private LocalDateTime createdAt;

}
