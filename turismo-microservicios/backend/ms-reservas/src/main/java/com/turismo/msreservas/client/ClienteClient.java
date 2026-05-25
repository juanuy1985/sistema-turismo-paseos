package com.turismo.msreservas.client;

import com.turismo.msreservas.dto.ClienteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-usuarios", path = "/api/clientes")
public interface ClienteClient {

    @GetMapping("/{id}")
    ClienteResponse obtenerPorId(@PathVariable("id") Long id);

    @GetMapping("/documento/{numeroDocumento}")
    ClienteResponse obtenerPorDocumento(@PathVariable("numeroDocumento") String numeroDocumento);
}
