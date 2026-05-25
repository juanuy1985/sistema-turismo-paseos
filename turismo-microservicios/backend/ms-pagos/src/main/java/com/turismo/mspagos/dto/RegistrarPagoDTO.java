package com.turismo.mspagos.dto;

import com.turismo.mspagos.model.MetodoPago;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrarPagoDTO {

    @NotNull(message = "El ID de reserva es obligatorio")
    @Positive(message = "El ID de reserva debe ser un número positivo")
    private Long reservaId;

    @NotBlank(message = "El código de reserva es obligatorio")
    @Size(max = 40, message = "El código de reserva no debe superar los 40 caracteres")
    private String codigoReserva;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    private BigDecimal monto;

    @NotBlank(message = "La moneda es obligatoria")
    @Size(min = 3, max = 3, message = "La moneda debe tener 3 caracteres (ej: PEN, USD)")
    private String moneda;

    @NotNull(message = "El método de pago es obligatorio")
    private MetodoPago metodoPago;

    @Size(max = 100, message = "El número de operación no debe superar los 100 caracteres")
    private String numeroOperacion;
}
