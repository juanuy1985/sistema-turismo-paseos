package com.turismo.msreservas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleReservaDTO {

    private Long id;
    private Integer cantidadPersonas;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    private List<PersonaReservaDTO> personas;
}
