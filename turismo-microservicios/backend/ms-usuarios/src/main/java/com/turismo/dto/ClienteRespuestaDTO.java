package com.turismo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteRespuestaDTO {

    private Long id;
    private String tipoDocumento;
    private String numeroDocumento;
    private String direccion;
    private UsuarioRespuestaDTO usuario;
}
