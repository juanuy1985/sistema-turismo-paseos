package com.turismo.mspagos.service;

import com.turismo.mspagos.dto.PagoResponse;
import com.turismo.mspagos.dto.RegistrarPagoDTO;
import com.turismo.mspagos.model.EstadoPago;

import java.time.LocalDateTime;
import java.util.List;

public interface PagoService {

    PagoResponse registrarPago(RegistrarPagoDTO dto);

    PagoResponse obtenerPorId(Long id);

    List<PagoResponse> listarPorReservaId(Long reservaId);

    List<PagoResponse> listarPorEstado(EstadoPago estado);

    List<PagoResponse> listarPorFecha(LocalDateTime desde, LocalDateTime hasta);
}
