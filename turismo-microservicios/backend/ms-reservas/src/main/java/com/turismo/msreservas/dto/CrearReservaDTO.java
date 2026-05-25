package com.turismo.msreservas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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
    @FutureOrPresent(message = "La fecha del paseo debe ser hoy o una fecha futura")
    private LocalDate fechaPaseo;

    @NotBlank(message = "La moneda es obligatoria")
    @Size(min = 3, max = 3, message = "La moneda debe tener 3 caracteres")
    private String moneda;

    @NotNull(message = "La cantidad de personas es obligatoria")
    @Min(value = 1, message = "La cantidad de personas debe ser al menos 1")
    private Integer cantidadPersonas;

    @NotNull(message = "La lista de personas es obligatoria")
    @Size(min = 1, message = "Debe registrar al menos una persona")
    @Valid
    private List<PersonaReservaDTO> personas;

    @AssertTrue(message = "La cantidad de personas debe coincidir con el número de personas registradas")
    public boolean isCantidadPersonasConsistente() {
        if (cantidadPersonas == null || personas == null) {
            return true;
        }
        return cantidadPersonas.equals(personas.size());
    }
}
