package com.turismo.msreservas.repository;

import com.turismo.msreservas.model.PersonaReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonaReservaRepository extends JpaRepository<PersonaReserva, Long> {

    List<PersonaReserva> findByDetalleReservaId(Long detalleReservaId);
}
