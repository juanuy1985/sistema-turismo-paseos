package com.turismo.msreservas.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "detalles_reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleReserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserva_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Reserva reserva;

    @Column(nullable = false)
    private Integer cantidadPersonas;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal precioUnitario;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    @OneToMany(mappedBy = "detalleReserva", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PersonaReserva> personas = new ArrayList<>();

    @PrePersist
    @PreUpdate
    protected void calcularSubtotal() {
        if (precioUnitario != null && cantidadPersonas != null) {
            subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidadPersonas));
        }
    }

    public void addPersona(PersonaReserva personaReserva) {
        personas.add(personaReserva);
        personaReserva.setDetalleReserva(this);
    }

    public void removePersona(PersonaReserva personaReserva) {
        personas.remove(personaReserva);
        personaReserva.setDetalleReserva(null);
    }
}
