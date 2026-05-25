package com.turismo.controller;

import com.turismo.dto.UsuarioActualizacionBasicaDTO;
import com.turismo.dto.UsuarioRegistroDTO;
import com.turismo.dto.UsuarioRespuestaDTO;
import com.turismo.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioRespuestaDTO> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public UsuarioRespuestaDTO buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarUsuarioPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioRespuestaDTO crear(@Valid @RequestBody UsuarioRegistroDTO dto) {
        return usuarioService.registrarUsuario(dto);
    }

    @PutMapping("/{id}")
    public UsuarioRespuestaDTO actualizar(@PathVariable Long id, @Valid @RequestBody UsuarioActualizacionBasicaDTO dto) {
        return usuarioService.actualizarDatosBasicos(id, dto);
    }

    @DeleteMapping("/{id}")
    public UsuarioRespuestaDTO desactivar(@PathVariable Long id) {
        return usuarioService.desactivarUsuario(id);
    }
}
