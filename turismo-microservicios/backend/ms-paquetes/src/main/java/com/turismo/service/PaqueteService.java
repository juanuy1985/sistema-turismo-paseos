package com.turismo.service;

import com.turismo.model.Paquete;
import com.turismo.repository.PaqueteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class PaqueteService {

    @Autowired
    private PaqueteRepository paqueteRepository;

    /**
     * Obtener todos los paquetes
     */
    @Transactional(readOnly = true)
    public List<Paquete> listarTodos() {
        log.info("Listando todos los paquetes");
        return paqueteRepository.findAll();
    }

    /**
     * Obtener todos los paquetes activos
     */
    @Transactional(readOnly = true)
    public List<Paquete> listarActivos() {
        log.info("Listando paquetes activos");
        return paqueteRepository.findByActivo(true);
    }

    /**
     * Buscar paquete por ID
     */
    @Transactional(readOnly = true)
    public Optional<Paquete> obtenerPorId(Long id) {
        log.info("Buscando paquete por ID: {}", id);
        if (id == null || id <= 0) {
            log.warn("ID inválido: {}", id);
            return Optional.empty();
        }
        return paqueteRepository.findById(id);
    }

    /**
     * Filtrar paquetes por destino
     */
    @Transactional(readOnly = true)
    public List<Paquete> filtrarPorDestino(Long destinoId) {
        log.info("Filtrando paquetes por destino: {}", destinoId);
        if (destinoId == null || destinoId <= 0) {
            log.warn("Destino ID inválido: {}", destinoId);
            return List.of();
        }
        return paqueteRepository.findByDestinoIdAndActivo(destinoId, true);
    }

    /**
     * Filtrar paquetes por destino (sin filtro de estado activo)
     */
    @Transactional(readOnly = true)
    public List<Paquete> filtrarPorDestinoTodos(Long destinoId) {
        log.info("Filtrando todos los paquetes por destino: {}", destinoId);
        if (destinoId == null || destinoId <= 0) {
            log.warn("Destino ID inválido: {}", destinoId);
            return List.of();
        }
        return paqueteRepository.findByDestinoId(destinoId);
    }

    /**
     * Filtrar paquetes por tipo de paseo
     */
    @Transactional(readOnly = true)
    public List<Paquete> filtrarPorTipoPaseo(Long tipoPaseoId) {
        log.info("Filtrando paquetes por tipo de paseo: {}", tipoPaseoId);
        if (tipoPaseoId == null || tipoPaseoId <= 0) {
            log.warn("Tipo de paseo ID inválido: {}", tipoPaseoId);
            return List.of();
        }
        return paqueteRepository.findByTipoPaseoIdAndActivo(tipoPaseoId, true);
    }

    /**
     * Filtrar paquetes por tipo de paseo (sin filtro de estado activo)
     */
    @Transactional(readOnly = true)
    public List<Paquete> filtrarPorTipoPaseoTodos(Long tipoPaseoId) {
        log.info("Filtrando todos los paquetes por tipo de paseo: {}", tipoPaseoId);
        if (tipoPaseoId == null || tipoPaseoId <= 0) {
            log.warn("Tipo de paseo ID inválido: {}", tipoPaseoId);
            return List.of();
        }
        return paqueteRepository.findByTipoPaseoId(tipoPaseoId);
    }

    /**
     * Filtrar paquetes por destino y tipo de paseo
     */
    @Transactional(readOnly = true)
    public List<Paquete> filtrarPorDestinoYTipoPaseo(Long destinoId, Long tipoPaseoId) {
        log.info("Filtrando paquetes por destino: {} y tipo de paseo: {}", destinoId, tipoPaseoId);
        if ((destinoId == null || destinoId <= 0) || (tipoPaseoId == null || tipoPaseoId <= 0)) {
            log.warn("Parámetros inválidos - Destino ID: {}, Tipo de paseo ID: {}", destinoId, tipoPaseoId);
            return List.of();
        }
        return paqueteRepository.findByDestinoIdAndTipoPaseoIdAndActivo(destinoId, tipoPaseoId, true);
    }

    /**
     * Filtrar paquetes por moneda
     */
    @Transactional(readOnly = true)
    public List<Paquete> filtrarPorMoneda(String moneda) {
        log.info("Filtrando paquetes por moneda: {}", moneda);
        if (moneda == null || moneda.trim().isEmpty()) {
            log.warn("Moneda inválida: {}", moneda);
            return List.of();
        }
        return paqueteRepository.findByMonedaAndActivo(moneda.toUpperCase(), true);
    }

    /**
     * Listar paquetes con cupos disponibles
     */
    @Transactional(readOnly = true)
    public List<Paquete> listarPaquetesConCuposDisponibles() {
        log.info("Listando paquetes con cupos disponibles");
        return paqueteRepository.findPaquetesConCuposDisponibles();
    }

    /**
     * Listar paquetes con cupos disponibles por destino
     */
    @Transactional(readOnly = true)
    public List<Paquete> listarPaquetesConCuposPorDestino(Long destinoId) {
        log.info("Listando paquetes con cupos disponibles por destino: {}", destinoId);
        if (destinoId == null || destinoId <= 0) {
            log.warn("Destino ID inválido: {}", destinoId);
            return List.of();
        }
        return paqueteRepository.findPaquetesConCuposPorDestino(destinoId);
    }

    /**
     * Validar si hay cupos disponibles en un paquete
     */
    @Transactional(readOnly = true)
    public boolean tieneCaposDisponibles(Long paqueteId) {
        log.info("Validando cupos disponibles para paquete: {}", paqueteId);
        if (paqueteId == null || paqueteId <= 0) {
            log.warn("Paquete ID inválido: {}", paqueteId);
            return false;
        }

        Optional<Paquete> paquete = paqueteRepository.findById(paqueteId);
        if (paquete.isEmpty()) {
            log.warn("Paquete no encontrado: {}", paqueteId);
            return false;
        }

        Paquete p = paquete.get();
        boolean tieneCupos = p.getCuposDisponibles() > p.getCuposReservados();
        log.info("Paquete {} - Cupos disponibles: {}, Reservados: {}, Tiene cupos: {}",
                paqueteId, p.getCuposDisponibles(), p.getCuposReservados(), tieneCupos);
        return tieneCupos;
    }

    /**
     * Obtener cantidad de cupos disponibles
     */
    @Transactional(readOnly = true)
    public Integer obtenerCuposDisponibles(Long paqueteId) {
        log.info("Obteniendo cupos disponibles del paquete: {}", paqueteId);
        if (paqueteId == null || paqueteId <= 0) {
            log.warn("Paquete ID inválido: {}", paqueteId);
            return 0;
        }

        Optional<Paquete> paquete = paqueteRepository.findById(paqueteId);
        if (paquete.isEmpty()) {
            log.warn("Paquete no encontrado: {}", paqueteId);
            return 0;
        }

        Paquete p = paquete.get();
        Integer cuposDisponibles = p.getCuposDisponibles() - p.getCuposReservados();
        log.info("Paquete {} - Cupos disponibles: {}", paqueteId, cuposDisponibles);
        return Math.max(cuposDisponibles, 0);
    }

    /**
     * Validar cupos y reservar (con transacción)
     */
    @Transactional
    public boolean reservarCupos(Long paqueteId, Integer cantidadCupos) {
        log.info("Intentando reservar {} cupos para paquete: {}", cantidadCupos, paqueteId);

        if (paqueteId == null || paqueteId <= 0) {
            log.warn("Paquete ID inválido: {}", paqueteId);
            return false;
        }

        if (cantidadCupos == null || cantidadCupos <= 0) {
            log.warn("Cantidad de cupos inválida: {}", cantidadCupos);
            return false;
        }

        Optional<Paquete> paquete = paqueteRepository.findById(paqueteId);
        if (paquete.isEmpty()) {
            log.warn("Paquete no encontrado: {}", paqueteId);
            return false;
        }

        Paquete p = paquete.get();
        Integer cuposDisponibles = p.getCuposDisponibles() - p.getCuposReservados();

        if (cuposDisponibles < cantidadCupos) {
            log.warn("Cupos insuficientes para paquete {}. Solicitados: {}, Disponibles: {}",
                    paqueteId, cantidadCupos, cuposDisponibles);
            return false;
        }

        p.setCuposDisponibles(p.getCuposDisponibles() - cantidadCupos);
        paqueteRepository.save(p);
        log.info("Reserva exitosa para paquete {}. Cupos disponibles restantes: {}", paqueteId, p.getCuposDisponibles());
        return true;
    }

    /**
     * Crear nuevo paquete
     */
    @Transactional
    public Paquete crear(Paquete paquete) {
        log.info("Creando nuevo paquete: {}", paquete.getTitulo());

        if (paquete == null || paquete.getTitulo() == null || paquete.getTitulo().trim().isEmpty()) {
            log.warn("Datos inválidos para crear paquete");
            throw new IllegalArgumentException("El paquete debe tener un título válido");
        }

        if (paquete.getDestino() == null || paquete.getTipoPaseo() == null) {
            log.warn("El paquete debe tener destino y tipo de paseo");
            throw new IllegalArgumentException("El paquete debe tener destino y tipo de paseo");
        }

        if (paquete.getCuposDisponibles() == null || paquete.getCuposDisponibles() <= 0) {
            log.warn("Cupos disponibles inválidos");
            throw new IllegalArgumentException("Los cupos disponibles deben ser mayores a 0");
        }

        paquete.setActivo(true);

        Paquete creado = paqueteRepository.save(paquete);
        log.info("Paquete creado exitosamente con ID: {}", creado.getId());
        return creado;
    }

    /**
     * Actualizar paquete existente
     */
    @Transactional
    public Paquete actualizar(Long id, Paquete paqueteActualizado) {
        log.info("Actualizando paquete con ID: {}", id);

        if (id == null || id <= 0) {
            log.warn("ID inválido: {}", id);
            throw new IllegalArgumentException("ID de paquete inválido");
        }

        Optional<Paquete> paqueteExistente = paqueteRepository.findById(id);
        if (paqueteExistente.isEmpty()) {
            log.warn("Paquete no encontrado para actualizar: {}", id);
            throw new IllegalArgumentException("Paquete no encontrado");
        }

        Paquete p = paqueteExistente.get();
        
        if (paqueteActualizado.getTitulo() != null && !paqueteActualizado.getTitulo().trim().isEmpty()) {
            p.setTitulo(paqueteActualizado.getTitulo());
        }
        
        if (paqueteActualizado.getDescripcion() != null) {
            p.setDescripcion(paqueteActualizado.getDescripcion());
        }
        
        if (paqueteActualizado.getPrecio() != null) {
            p.setPrecio(paqueteActualizado.getPrecio());
        }
        
        if (paqueteActualizado.getMoneda() != null) {
            p.setMoneda(paqueteActualizado.getMoneda());
        }
        
        if (paqueteActualizado.getDuracionDias() != null && paqueteActualizado.getDuracionDias() > 0) {
            p.setDuracionDias(paqueteActualizado.getDuracionDias());
        }
        
        if (paqueteActualizado.getActivo() != null) {
            p.setActivo(paqueteActualizado.getActivo());
        }

        Paquete actualizado = paqueteRepository.save(p);
        log.info("Paquete actualizado exitosamente: {}", id);
        return actualizado;
    }

    /**
     * Cambiar estado del paquete
     */
    @Transactional
    public Paquete cambiarEstado(Long id, Boolean estado) {
        log.info("Cambiando estado del paquete {} a: {}", id, estado);

        if (id == null || id <= 0) {
            log.warn("ID inválido: {}", id);
            throw new IllegalArgumentException("ID de paquete inválido");
        }

        Optional<Paquete> paquete = paqueteRepository.findById(id);
        if (paquete.isEmpty()) {
            log.warn("Paquete no encontrado: {}", id);
            throw new IllegalArgumentException("Paquete no encontrado");
        }

        Paquete p = paquete.get();
        p.setActivo(estado);
        Paquete actualizado = paqueteRepository.save(p);
        log.info("Estado del paquete {} cambiado exitosamente a: {}", id, estado);
        return actualizado;
    }

    /**
     * Contar paquetes activos por destino
     */
    @Transactional(readOnly = true)
    public Long contarPorDestino(Long destinoId) {
        log.info("Contando paquetes activos por destino: {}", destinoId);
        if (destinoId == null || destinoId <= 0) {
            log.warn("Destino ID inválido: {}", destinoId);
            return 0L;
        }
        return paqueteRepository.countByDestinoIdAndActivo(destinoId, true);
    }

    /**
     * Obtener destino ID del paquete
     */
    @Transactional(readOnly = true)
    public Long obtenerDestinoId(Long paqueteId) {
        log.info("Obteniendo destino del paquete: {}", paqueteId);
        Optional<Paquete> paquete = paqueteRepository.findById(paqueteId);
        return paquete.map(p -> p.getDestino().getId()).orElse(null);
    }
}
