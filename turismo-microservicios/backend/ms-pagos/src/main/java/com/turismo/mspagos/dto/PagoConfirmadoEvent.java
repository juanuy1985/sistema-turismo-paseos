package com.turismo.mspagos.dto;

import com.turismo.mspagos.model.EstadoPago;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoConfirmadoEvent {
    private Long pagoId;
    private Long reservaId;
    private String codigoReserva;
    private BigDecimal monto;
    private String moneda;
    private EstadoPago estado;
    private String numeroOperacion;
    private LocalDateTime fechaPago;
}
