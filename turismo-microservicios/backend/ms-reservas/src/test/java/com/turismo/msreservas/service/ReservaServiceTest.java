package com.turismo.msreservas.service;

import com.turismo.msreservas.client.ClienteClient;
import com.turismo.msreservas.client.PaqueteClient;
import com.turismo.msreservas.dto.ClienteResponse;
import com.turismo.msreservas.dto.CrearReservaDTO;
import com.turismo.msreservas.dto.PaqueteResponse;
import com.turismo.msreservas.dto.PersonaReservaDTO;
import com.turismo.msreservas.dto.ReservaDTO;
import com.turismo.msreservas.exception.RecursoNoEncontradoException;
import com.turismo.msreservas.exception.ReglaNegocioException;
import com.turismo.msreservas.model.EstadoReserva;
import com.turismo.msreservas.model.Reserva;
import com.turismo.msreservas.publisher.ReservaCreadaPublisher;
import com.turismo.msreservas.repository.ReservaRepository;
import feign.FeignException;
import feign.Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private ClienteClient clienteClient;

    @Mock
    private PaqueteClient paqueteClient;

    @Mock
    private ReservaCreadaPublisher reservaCreadaPublisher;

    @InjectMocks
    private ReservaService reservaService;

    private static Request dummyRequest() {
        return Request.create(Request.HttpMethod.GET, "/test", Collections.emptyMap(), null, null, null);
    }

    private CrearReservaDTO crearDTO() {
        PersonaReservaDTO persona = PersonaReservaDTO.builder()
                .nombres("Juan")
                .apellidos("Perez")
                .tipoDocumento("DNI")
                .numeroDocumento("12345678")
                .edad(30)
                .build();
        CrearReservaDTO dto = new CrearReservaDTO();
        dto.setClienteId(1L);
        dto.setPaqueteId(10L);
        dto.setFechaReserva(LocalDate.now());
        dto.setFechaPaseo(LocalDate.now().plusDays(5));
        dto.setMoneda("PEN");
        dto.setCantidadPersonas(1);
        dto.setPersonas(List.of(persona));
        return dto;
    }

    private PaqueteResponse paqueteActivo(int cuposDisponibles) {
        return new PaqueteResponse(10L, "Paquete Test", new BigDecimal("150.00"), "PEN",
                cuposDisponibles, true, "Lima");
    }

    @Test
    void crear_registra_reserva_exitosamente() {
        when(clienteClient.obtenerPorId(1L)).thenReturn(new ClienteResponse());
        when(paqueteClient.obtenerPorId(10L)).thenReturn(paqueteActivo(5));
        when(reservaRepository.findByCodigoReserva(anyString())).thenReturn(Optional.empty());
        when(reservaRepository.save(any(Reserva.class))).thenAnswer(inv -> inv.getArgument(0));

        ReservaDTO resultado = reservaService.crear(crearDTO());

        assertThat(resultado).isNotNull();
        assertThat(resultado.getClienteId()).isEqualTo(1L);
        assertThat(resultado.getPaqueteId()).isEqualTo(10L);
        assertThat(resultado.getEstado()).isEqualTo(EstadoReserva.PENDIENTE);
        assertThat(resultado.getMontoTotal()).isEqualByComparingTo(new BigDecimal("150.00"));
        verify(reservaCreadaPublisher).publicar(any(Reserva.class));
    }

    @Test
    void crear_lanza_excepcion_cuando_cliente_no_existe() {
        when(clienteClient.obtenerPorId(anyLong()))
                .thenThrow(new FeignException.NotFound("Not Found", dummyRequest(), null, null));

        assertThatThrownBy(() -> reservaService.crear(crearDTO()))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("Cliente no encontrado");
    }

    @Test
    void crear_lanza_excepcion_cuando_paquete_no_existe() {
        when(clienteClient.obtenerPorId(1L)).thenReturn(new ClienteResponse());
        when(paqueteClient.obtenerPorId(anyLong()))
                .thenThrow(new FeignException.NotFound("Not Found", dummyRequest(), null, null));

        assertThatThrownBy(() -> reservaService.crear(crearDTO()))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("Paquete no encontrado");
    }

    @Test
    void crear_lanza_excepcion_cuando_cupos_insuficientes() {
        when(clienteClient.obtenerPorId(1L)).thenReturn(new ClienteResponse());
        when(paqueteClient.obtenerPorId(10L)).thenReturn(paqueteActivo(0));

        assertThatThrownBy(() -> reservaService.crear(crearDTO()))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessageContaining("cupos suficientes");
    }
}
