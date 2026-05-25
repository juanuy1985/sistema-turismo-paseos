package com.turismo.mspagos.repository;

import com.turismo.mspagos.model.MetodoPago;
import com.turismo.mspagos.model.Pago;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

@org.springframework.stereotype.Repository
public interface MetodoPagoRepository extends Repository<Pago, Long> {

    @Query("SELECT DISTINCT p.metodoPago FROM Pago p ORDER BY p.metodoPago")
    List<MetodoPago> findAllMetodosPago();
}
