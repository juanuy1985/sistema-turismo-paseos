package com.turismo.msnotificaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoConfirmadoEventDto {
    private Long pagoId;
    private Long reservaId;
    private String codigoReserva;
    private BigDecimal monto;
    private String moneda;
    private String estado;
    private String numeroOperacion;
    private LocalDateTime fechaPago;
}
