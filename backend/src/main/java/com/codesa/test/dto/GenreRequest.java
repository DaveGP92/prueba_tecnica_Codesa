package com.codesa.test.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GenreRequest {

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 100, message = "La descripción debe tener máximo 100 caracteres")
    private String description;

}
