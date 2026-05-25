package com.turismo.msreservas.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "personas_reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonaReserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detalle_reserva_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private DetalleReserva detalleReserva;

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(nullable = false, length = 20)
    private String tipoDocumento;

    @Column(nullable = false, length = 30)
    private String numeroDocumento;

    @Column(nullable = false)
    private Integer edad;
}
