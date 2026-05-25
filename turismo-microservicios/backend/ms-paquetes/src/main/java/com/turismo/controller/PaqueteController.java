package com.turismo.controller;

import com.turismo.dto.PaqueteDTO;
import com.turismo.mapper.PaqueteMapper;
import com.turismo.service.PaqueteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Paquetes", description = "API REST para la gestión de paquetes turísticos")
public class PaqueteController {

    private final PaqueteService paqueteService;
    private final PaqueteMapper paqueteMapper;

    @GetMapping
    @Operation(summary = "Listar todos los paquetes")
    public ResponseEntity<List<PaqueteDTO>> listarTodos() {
        log.info("GET /api/paquetes - Listando todos los paquetes");
        List<PaqueteDTO> paquetes = paqueteMapper.toDTOList(paqueteService.listarTodos());
        return ResponseEntity.ok(paquetes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener paquete por ID")
    public ResponseEntity<PaqueteDTO> obtenerPorId(@PathVariable Long id) {
        log.info("GET /api/paquetes/{} - Buscando paquete por ID", id);
        return paqueteService.obtenerPorId(id)
                .map(paquete -> ResponseEntity.ok(paqueteMapper.toDTO(paquete)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/destino/{destinoId}")
    @Operation(summary = "Listar paquetes por destino")
    public ResponseEntity<List<PaqueteDTO>> listarPorDestino(@PathVariable Long destinoId) {
        log.info("GET /api/paquetes/destino/{} - Listando paquetes por destino", destinoId);
        List<PaqueteDTO> paquetes = paqueteMapper.toDTOList(paqueteService.filtrarPorDestinoTodos(destinoId));
        return ResponseEntity.ok(paquetes);
    }

    @GetMapping("/tipo/{tipoPaseoId}")
    @Operation(summary = "Listar paquetes por tipo de paseo")
    public ResponseEntity<List<PaqueteDTO>> listarPorTipoPaseo(@PathVariable Long tipoPaseoId) {
        log.info("GET /api/paquetes/tipo/{} - Listando paquetes por tipo de paseo", tipoPaseoId);
        List<PaqueteDTO> paquetes = paqueteMapper.toDTOList(paqueteService.filtrarPorTipoPaseoTodos(tipoPaseoId));
        return ResponseEntity.ok(paquetes);
    }

    @GetMapping("/activos")
    @Operation(summary = "Listar paquetes activos")
    public ResponseEntity<List<PaqueteDTO>> listarActivos() {
        log.info("GET /api/paquetes/activos - Listando paquetes activos");
        List<PaqueteDTO> paquetes = paqueteMapper.toDTOList(paqueteService.listarActivos());
        return ResponseEntity.ok(paquetes);
    }
}
