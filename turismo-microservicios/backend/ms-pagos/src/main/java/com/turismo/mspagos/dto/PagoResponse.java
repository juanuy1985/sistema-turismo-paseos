package com.turismo.mspagos.dto;

import com.turismo.mspagos.model.EstadoPago;
import com.turismo.mspagos.model.MetodoPago;
import jakarta.validation.Valid;
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
public class PagoResponse {

    private Long id;
    private Long reservaId;
    private String codigoReserva;
    private BigDecimal monto;
    private String moneda;
    private EstadoPago estado;
    private LocalDateTime fechaPago;
    private MetodoPago metodoPago;
    private String numeroOperacion;
    @Valid
    private ComprobanteDTO comprobante;
    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;
}
