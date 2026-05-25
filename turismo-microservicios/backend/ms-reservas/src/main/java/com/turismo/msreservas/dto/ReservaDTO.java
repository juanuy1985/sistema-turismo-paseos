package com.turismo.msreservas.dto;

import com.turismo.msreservas.model.EstadoReserva;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaDTO {

    private Long id;
    private Long usuarioId;
    private Long paqueteId;
    private LocalDate fechaReserva;
    private Integer cantidadPersonas;
    private BigDecimal precioTotal;
    private EstadoReserva estado;
    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;
}
