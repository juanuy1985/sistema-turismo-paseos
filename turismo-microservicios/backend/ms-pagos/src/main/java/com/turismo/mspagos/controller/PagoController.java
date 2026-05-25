package com.turismo.mspagos.controller;

import com.turismo.mspagos.dto.PagoResponse;
import com.turismo.mspagos.dto.RegistrarPagoDTO;
import com.turismo.mspagos.model.EstadoPago;
import com.turismo.mspagos.service.PagoService;
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
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Pagos", description = "API REST para la gestión de pagos de reservas turísticas")
public class PagoController {

    private final PagoService pagoService;

    @PostMapping
    @Operation(summary = "Procesar un nuevo pago")
    public ResponseEntity<PagoResponse> procesarPago(@Valid @RequestBody RegistrarPagoDTO dto) {
        log.info("POST /api/pagos - Procesando pago para reservaId={}", dto.getReservaId());
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.registrarPago(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar detalle de un pago por ID")
    public ResponseEntity<PagoResponse> obtenerPorId(@PathVariable Long id) {
        log.info("GET /api/pagos/{} - Consultando pago por ID", id);
        return ResponseEntity.ok(pagoService.obtenerPorId(id));
    }

    @GetMapping("/reserva/{reservaId}")
    @Operation(summary = "Consultar pagos por ID de reserva")
    public ResponseEntity<List<PagoResponse>> listarPorReserva(@PathVariable Long reservaId) {
        log.info("GET /api/pagos/reserva/{} - Listando pagos por reservaId", reservaId);
        return ResponseEntity.ok(pagoService.listarPorReservaId(reservaId));
    }

    @GetMapping("/codigo-reserva/{codigoReserva}")
    @Operation(summary = "Consultar pagos por código de reserva")
    public ResponseEntity<List<PagoResponse>> listarPorCodigoReserva(@PathVariable String codigoReserva) {
        log.info("GET /api/pagos/codigo-reserva/{} - Listando pagos por código de reserva", codigoReserva);
        return ResponseEntity.ok(pagoService.listarPorCodigoReserva(codigoReserva));
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Consultar pagos por estado")
    public ResponseEntity<List<PagoResponse>> listarPorEstado(@PathVariable EstadoPago estado) {
        log.info("GET /api/pagos/estado/{} - Listando pagos por estado", estado);
        return ResponseEntity.ok(pagoService.listarPorEstado(estado));
    }
}
