package com.turismo.msreservas.repository;

import com.turismo.msreservas.model.EstadoReserva;
import com.turismo.msreservas.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByClienteId(Long clienteId);

    List<Reserva> findByPaqueteId(Long paqueteId);

    List<Reserva> findByEstado(EstadoReserva estado);
}
