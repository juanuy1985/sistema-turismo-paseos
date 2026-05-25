package com.turismo.msreservas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearReservaDTO {

    @NotNull(message = "El ID de cliente es obligatorio")
    private Long clienteId;

    @NotNull(message = "El ID de paquete es obligatorio")
    private Long paqueteId;

    @NotNull(message = "La fecha de reserva es obligatoria")
    private LocalDate fechaReserva;

    @NotNull(message = "La fecha del paseo es obligatoria")
    private LocalDate fechaPaseo;

    @NotBlank(message = "La moneda es obligatoria")
    @Size(min = 3, max = 3, message = "La moneda debe tener 3 caracteres")
    private String moneda;

    @NotNull(message = "La cantidad de personas es obligatoria")
    @Min(value = 1, message = "La cantidad de personas debe ser al menos 1")
    private Integer cantidadPersonas;
}
