package com.turismo.msreservas.dto;

import com.turismo.msreservas.model.EstadoReserva;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaCreadaEvent {
    private Long reservaId;
    private String codigoReserva;
    private Long clienteId;
    private Long paqueteId;
    private BigDecimal montoTotal;
    private String moneda;
    private LocalDate fechaPaseo;
    private EstadoReserva estado;
}
