package com.turismo.msreservas.controller;

import com.turismo.msreservas.dto.CrearReservaDTO;
import com.turismo.msreservas.dto.ReservaDTO;
import com.turismo.msreservas.model.EstadoReserva;
import com.turismo.msreservas.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Reservas", description = "API REST para la gestión de reservas turísticas")
public class ReservaController {

    private final ReservaService reservaService;

    @GetMapping
    @Operation(summary = "Listar todas las reservas")
    public ResponseEntity<List<ReservaDTO>> listarTodas() {
        log.info("GET /api/reservas - Listando todas las reservas");
        return ResponseEntity.ok(reservaService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener reserva por ID")
    public ResponseEntity<ReservaDTO> obtenerPorId(@PathVariable Long id) {
        log.info("GET /api/reservas/{} - Buscando reserva por ID", id);
        return ResponseEntity.ok(reservaService.obtenerPorId(id));
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar reservas por cliente")
    public ResponseEntity<List<ReservaDTO>> listarPorCliente(@PathVariable Long clienteId) {
        log.info("GET /api/reservas/cliente/{} - Listando reservas por cliente", clienteId);
        return ResponseEntity.ok(reservaService.listarPorCliente(clienteId));
    }

    @PostMapping
    @Operation(summary = "Crear una nueva reserva")
    public ResponseEntity<ReservaDTO> crear(@Valid @RequestBody CrearReservaDTO dto) {
        log.info("POST /api/reservas - Creando reserva para cliente {}", dto.getClienteId());
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.crear(dto));
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Actualizar estado de una reserva")
    public ResponseEntity<ReservaDTO> actualizarEstado(
            @PathVariable Long id,
            @RequestParam EstadoReserva estado) {
        log.info("PATCH /api/reservas/{}/estado - Actualizando estado a {}", id, estado);
        return ResponseEntity.ok(reservaService.actualizarEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar una reserva")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        log.info("DELETE /api/reservas/{} - Cancelando reserva", id);
        reservaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }
}
