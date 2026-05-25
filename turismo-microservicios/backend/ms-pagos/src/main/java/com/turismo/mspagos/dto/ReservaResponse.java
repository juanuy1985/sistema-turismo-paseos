package com.turismo.mspagos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaResponse {

    private Long id;
    private String codigoReserva;
    private Long clienteId;
    private Long paqueteId;
    private BigDecimal montoTotal;
    private String moneda;
    private String estado;
}
