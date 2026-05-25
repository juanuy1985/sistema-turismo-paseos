package com.turismo.msreservas.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonaReservaDTO {

    private static final int MAX_EDAD_PERMITIDA = 120;

    private Long id;

    @NotBlank(message = "Los nombres de la persona son obligatorios")
    private String nombres;

    @NotBlank(message = "Los apellidos de la persona son obligatorios")
    private String apellidos;

    @NotBlank(message = "El tipo de documento de la persona es obligatorio")
    private String tipoDocumento;

    @NotBlank(message = "El número de documento de la persona es obligatorio")
    private String numeroDocumento;

    @NotNull(message = "La edad de la persona es obligatoria")
    @Min(value = 0, message = "La edad de la persona no puede ser negativa")
    @Max(value = MAX_EDAD_PERMITIDA, message = "La edad de la persona no puede superar 120 años")
    private Integer edad;
}
