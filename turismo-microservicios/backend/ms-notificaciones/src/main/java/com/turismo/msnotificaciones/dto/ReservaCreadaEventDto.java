package com.turismo.msnotificaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaCreadaEventDto {
    private Long reservaId;
    private String codigoReserva;
    private Long clienteId;
    private Long paqueteId;
    private BigDecimal montoTotal;
    private String moneda;
    private LocalDate fechaPaseo;
    private String estado;
}
