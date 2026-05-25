package com.turismo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Los nombres son requeridos")
    @Column(nullable = false, length = 100)
    private String nombres;

    @NotBlank(message = "Los apellidos son requeridos")
    @Column(nullable = false, length = 100)
    private String apellidos;

    @NotBlank(message = "El email es requerido")
    @Email(message = "El email no es válido")
    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @NotBlank(message = "El teléfono es requerido")
    @Column(nullable = false, length = 20)
    private String telefono;

    @NotBlank(message = "El username es requerido")
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @NotBlank(message = "La contraseña es requerida")
    @Column(nullable = false, length = 255)
    private String password;

    @NotNull(message = "El estado activo es requerido")
    @Column(nullable = false)
    private Boolean activo = true;

    @NotNull(message = "El rol es requerido")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;
}
