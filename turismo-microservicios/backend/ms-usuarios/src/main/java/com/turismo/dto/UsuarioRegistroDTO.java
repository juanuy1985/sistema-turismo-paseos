package com.turismo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioRegistroDTO {

    @NotBlank(message = "Los nombres son requeridos")
    @Size(max = 100, message = "Los nombres no deben exceder 100 caracteres")
    private String nombres;

    @NotBlank(message = "Los apellidos son requeridos")
    @Size(max = 100, message = "Los apellidos no deben exceder 100 caracteres")
    private String apellidos;

    @NotBlank(message = "El email es requerido")
    @Email(message = "El email no es válido")
    @Size(max = 120, message = "El email no debe exceder 120 caracteres")
    private String email;

    @NotBlank(message = "El teléfono es requerido")
    @Size(max = 20, message = "El teléfono no debe exceder 20 caracteres")
    @Pattern(
            regexp = "^\\+?[0-9]{7,20}$",
            message = "El teléfono debe contener entre 7 y 20 dígitos y puede iniciar con +"
    )
    private String telefono;

    @NotBlank(message = "El username es requerido")
    @Size(min = 3, max = 50, message = "El username debe tener entre 3 y 50 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "El username solo permite letras, números, punto, guión y guión bajo")
    private String username;

    @NotBlank(message = "La contraseña es requerida")
    @Size(min = 8, max = 255, message = "La contraseña debe tener entre 8 y 255 caracteres")
    private String password;

    @NotNull(message = "El estado activo es requerido")
    private Boolean activo;

    @NotNull(message = "El rol es requerido")
    private Long rolId;
}
