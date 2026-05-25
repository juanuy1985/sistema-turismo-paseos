package com.turismo.mspagos.repository;

import com.turismo.mspagos.model.MetodoPago;
import com.turismo.mspagos.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetodoPagoRepository extends JpaRepository<Pago, Long> {

    @Query("SELECT DISTINCT p.metodoPago FROM Pago p ORDER BY p.metodoPago")
    List<MetodoPago> findAllMetodosPago();
}
