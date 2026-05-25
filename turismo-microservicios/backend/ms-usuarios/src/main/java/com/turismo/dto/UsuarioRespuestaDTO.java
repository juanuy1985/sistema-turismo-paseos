package com.turismo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioRespuestaDTO {

    private Long id;
    private String nombres;
    private String apellidos;
    private String email;
    private String telefono;
    private String username;
    private Boolean activo;
    private Long rolId;
    private String rolNombre;
}
