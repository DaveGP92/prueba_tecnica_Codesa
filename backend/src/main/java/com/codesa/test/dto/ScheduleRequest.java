package com.codesa.test.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.sql.Date;

@Data
public class ScheduleRequest {

    @NotNull(message = "El id de la obra es obligatorio")
    @Positive(message = "El id de la obra debe ser mayor que cero")
    private Long playId;

    @NotNull(message = "La fecha y hora es obligatoria")
    private Date dateTime;

    @NotBlank(message = "La sala es obligatoria")
    @Size(max = 100, message = "La sala debe tener máximo 100 caracteres")
    private String room;

    @NotBlank(message = "El total de asientos es obligatorio")
    private String totalSeats;

    @NotBlank(message = "Los asientos disponibles son obligatorios")
    private String availableSeats;

    @NotBlank(message = "El precio base es obligatorio")
    private String basePrice;

}
