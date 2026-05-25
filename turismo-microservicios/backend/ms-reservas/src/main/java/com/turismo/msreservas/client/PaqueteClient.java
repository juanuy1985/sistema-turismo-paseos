package com.turismo.msreservas.client;

import com.turismo.msreservas.dto.PaqueteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-paquetes", path = "/api/paquetes")
public interface PaqueteClient {

    @GetMapping("/{id}")
    PaqueteResponse obtenerPorId(@PathVariable("id") Long id);
}
