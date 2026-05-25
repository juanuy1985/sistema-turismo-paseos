package com.turismo.mspagos.client;

import com.turismo.mspagos.dto.ReservaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-reservas", path = "/api/reservas")
public interface ReservaClient {

    @GetMapping("/{id}")
    ReservaResponse obtenerPorId(@PathVariable("id") Long id);

    @GetMapping("/codigo/{codigoReserva}")
    ReservaResponse obtenerPorCodigo(@PathVariable("codigoReserva") String codigoReserva);
}
