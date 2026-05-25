package com.turismo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El tipo de documento es requerido")
    @Column(name = "tipo_documento", nullable = false, length = 30)
    private String tipoDocumento;

    @NotBlank(message = "El número de documento es requerido")
    @Column(name = "numero_documento", nullable = false, unique = true, length = 30)
    private String numeroDocumento;

    @NotBlank(message = "La dirección es requerida")
    @Column(nullable = false, length = 200)
    private String direccion;

    @NotNull(message = "El usuario es requerido")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;
}
