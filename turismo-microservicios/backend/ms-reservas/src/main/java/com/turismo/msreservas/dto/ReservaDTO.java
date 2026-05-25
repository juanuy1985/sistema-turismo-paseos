package com.turismo.msreservas.dto;

import com.turismo.msreservas.model.EstadoReserva;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaDTO {

    private Long id;
    private Long clienteId;
    private Long paqueteId;
    private LocalDate fechaReserva;
    private LocalDate fechaPaseo;
    private EstadoReserva estado;
    private String moneda;
    private BigDecimal montoTotal;
    private String codigoReserva;
    private List<DetalleReservaDTO> detalles;
    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;
}
