package com.turismo.msreservas.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaqueteResponse {

    private Long id;
    private String titulo;
    private BigDecimal precio;
    private String moneda;
    private Integer cuposDisponibles;

    @JsonAlias("estadoActivo")
    private Boolean activo;

    @JsonAlias("destinoNombre")
    private String destino;
}
