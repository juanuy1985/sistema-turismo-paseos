package com.turismo.msreservas.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {

    private Long id;
    private String nombres;
    private String apellidos;
    private String email;
    private String telefono;
    private String numeroDocumento;
    private Boolean activo;

    @JsonProperty("usuario")
    private void extraerUsuario(UsuarioClienteResponse usuario) {
        if (usuario == null) {
            return;
        }
        this.nombres = usuario.getNombres();
        this.apellidos = usuario.getApellidos();
        this.email = usuario.getEmail();
        this.telefono = usuario.getTelefono();
        this.activo = usuario.getActivo();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class UsuarioClienteResponse {
        private String nombres;
        private String apellidos;
        private String email;
        private String telefono;
        private Boolean activo;
    }
}
