package com.turismo.mspagos.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "comprobantes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comprobante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String serie;

    @Column(nullable = false, length = 20)
    private String numero;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoComprobante tipo;

    @Column(nullable = false)
    private LocalDateTime fechaEmision;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal montoTotal;

    @OneToOne(mappedBy = "comprobante")
    private Pago pago;

    @PrePersist
    protected void onCreate() {
        if (fechaEmision == null) {
            fechaEmision = LocalDateTime.now();
        }
    }
}
