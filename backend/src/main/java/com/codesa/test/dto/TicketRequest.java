package com.codesa.test.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class TicketRequest {

    @NotNull(message = "El id de la función es obligatorio")
    @Positive(message = "El id de la función debe ser mayor que cero")
    private Long scheduleId;

    @Positive(message = "El id del usuario debe ser mayor que cero")
    private Long userId;

    private String customerName;

    @Email(message = "El correo electrónico debe ser válido")
    private String customerEmail;

    @Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe tener 10 dígitos")
    private String customerPhone;

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor que cero")
    private Long quantity;

    @NotNull(message = "El precio total es obligatorio")
    @Positive(message = "El precio total debe ser mayor que cero")
    private Float totalPrice;

}
