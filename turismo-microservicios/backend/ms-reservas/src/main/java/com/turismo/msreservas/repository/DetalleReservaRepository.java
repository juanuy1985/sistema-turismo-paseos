package com.turismo.msreservas.repository;

import com.turismo.msreservas.model.DetalleReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleReservaRepository extends JpaRepository<DetalleReserva, Long> {

    List<DetalleReserva> findByReservaId(Long reservaId);
}
