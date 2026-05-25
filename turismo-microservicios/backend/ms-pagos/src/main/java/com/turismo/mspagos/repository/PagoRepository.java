package com.turismo.mspagos.repository;

import com.turismo.mspagos.model.EstadoPago;
import com.turismo.mspagos.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByReservaId(Long reservaId);

    Optional<Pago> findByCodigoReserva(String codigoReserva);

    List<Pago> findByEstado(EstadoPago estado);

    List<Pago> findByNumeroOperacion(String numeroOperacion);

    List<Pago> findByFechaPagoBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
