package com.codesa.test.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PlayRequest {

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 150, message = "El título debe tener máximo 150 caracteres")
    private String title;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 1000, message = "La descripción debe tener máximo 1000 caracteres")
    private String description;

    @NotNull(message = "El id del género es obligatorio")
    @Positive(message = "El id del género debe ser mayor que cero")
    private Long genreId;

    @NotNull(message = "La duración en minutos es obligatoria")
    @Positive(message = "La duración en minutos debe ser mayor que cero")
    private Long durationMinutes;

}
