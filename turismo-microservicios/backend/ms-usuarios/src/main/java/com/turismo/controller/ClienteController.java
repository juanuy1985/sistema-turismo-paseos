package com.turismo.controller;

import com.turismo.dto.ClienteRegistroDTO;
import com.turismo.dto.ClienteRespuestaDTO;
import com.turismo.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final UsuarioService usuarioService;

    @GetMapping("/documento/{numeroDocumento}")
    public ClienteRespuestaDTO buscarPorDocumento(@PathVariable String numeroDocumento) {
        return usuarioService.buscarClientePorDocumento(numeroDocumento);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteRespuestaDTO crear(@Valid @RequestBody ClienteRegistroDTO dto) {
        return usuarioService.registrarCliente(dto);
    }
}
