package com.turismo.mapper;

import com.turismo.dto.PaqueteDTO;
import com.turismo.model.Paquete;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaqueteMapper {

    public PaqueteDTO toDTO(Paquete paquete) {
        if (paquete == null) {
            return null;
        }

        return PaqueteDTO.builder()
                .id(paquete.getId())
                .titulo(paquete.getTitulo())
                .descripcion(paquete.getDescripcion())
                .destinoId(paquete.getDestino() != null ? paquete.getDestino().getId() : null)
                .destinoNombre(paquete.getDestino() != null ? paquete.getDestino().getNombre() : null)
                .tipoPaseoId(paquete.getTipoPaseo() != null ? paquete.getTipoPaseo().getId() : null)
                .tipoPaseoNombre(paquete.getTipoPaseo() != null ? paquete.getTipoPaseo().getNombre() : null)
                .precio(paquete.getPrecio())
                .moneda(paquete.getMoneda())
                .duracionDias(paquete.getDuracionDias())
                .cuposDisponibles(paquete.getCuposDisponibles())
                .cuposReservados(paquete.getCuposReservados())
                .cuposActuales(paquete.getCuposDisponibles())
                .estadoActivo(paquete.getActivo())
                .fechaCreacion(paquete.getFechaCreacion())
                .fechaActualizacion(paquete.getFechaActualizacion())
                .build();
    }

    public List<PaqueteDTO> toDTOList(List<Paquete> paquetes) {
        return paquetes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
