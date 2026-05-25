package com.turismo.msreservas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaqueteDTO {

    private Long id;
    private String nombre;
    private BigDecimal precio;
    private Integer capacidad;
    private Integer disponibles;
    private Boolean activo;
}
