package com.turismo.msreservas.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "reservas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long clienteId;

    @Column(nullable = false)
    private Long paqueteId;

    @Column(nullable = false)
    private LocalDate fechaReserva;

    @Column(nullable = false)
    private LocalDate fechaPaseo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoReserva estado;

    @Column(nullable = false, length = 3)
    private String moneda;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal montoTotal;

    @Column(nullable = false, unique = true, length = 40)
    private String codigoReserva;

    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DetalleReserva> detalles = new ArrayList<>();

    @Column(updatable = false)
    private LocalDateTime creadoEn;

    private LocalDateTime actualizadoEn;

    @PrePersist
    protected void onCreate() {
        creadoEn = LocalDateTime.now();
        actualizadoEn = LocalDateTime.now();
        if (estado == null) {
            estado = EstadoReserva.PENDIENTE;
        }
        if (codigoReserva == null || codigoReserva.isBlank()) {
            codigoReserva = "RES-" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        actualizadoEn = LocalDateTime.now();
    }

    public void addDetalle(DetalleReserva detalle) {
        detalles.add(detalle);
        detalle.setReserva(this);
    }

    public void removeDetalle(DetalleReserva detalle) {
        detalles.remove(detalle);
        detalle.setReserva(null);
    }
}
