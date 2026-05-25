package com.turismo.repository;

import com.turismo.model.Paquete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaqueteRepository extends JpaRepository<Paquete, Long> {

    /**
     * Buscar paquetes por destino
     */
    List<Paquete> findByDestinoId(Long destinoId);

    /**
     * Buscar paquetes por destino y estado activo
     */
    List<Paquete> findByDestinoIdAndActivo(Long destinoId, Boolean activo);

    /**
     * Buscar paquetes por tipo de paseo
     */
    List<Paquete> findByTipoPaseoId(Long tipoPaseoId);

    /**
     * Buscar paquetes por tipo de paseo y estado activo
     */
    List<Paquete> findByTipoPaseoIdAndActivo(Long tipoPaseoId, Boolean activo);

    /**
     * Buscar paquetes por moneda
     */
    List<Paquete> findByMoneda(String moneda);

    /**
     * Buscar paquetes por moneda y estado activo
     */
    List<Paquete> findByMonedaAndActivo(String moneda, Boolean activo);

    /**
     * Buscar paquetes por estado activo
     */
    List<Paquete> findByActivo(Boolean activo);

    /**
     * Buscar paquetes por destino y tipo de paseo
     */
    List<Paquete> findByDestinoIdAndTipoPaseoId(Long destinoId, Long tipoPaseoId);

    /**
     * Buscar paquetes por destino, tipo de paseo y estado activo
     */
    List<Paquete> findByDestinoIdAndTipoPaseoIdAndActivo(
            Long destinoId, Long tipoPaseoId, Boolean activo);

    /**
     * Buscar paquetes con cupos disponibles
     */
    @Query("SELECT p FROM Paquete p WHERE p.cuposDisponibles > 0 AND p.activo = true")
    List<Paquete> findPaquetesConCuposDisponibles();

    /**
     * Buscar paquetes con cupos disponibles por destino
     */
    @Query("SELECT p FROM Paquete p WHERE p.destino.id = :destinoId " +
           "AND p.cuposDisponibles > 0 AND p.activo = true")
    List<Paquete> findPaquetesConCuposPorDestino(@Param("destinoId") Long destinoId);

    /**
     * Buscar paquetes por título (búsqueda parcial)
     */
    List<Paquete> findByTituloContainingIgnoreCase(String titulo);

    /**
     * Buscar paquetes por título y estado activo
     */
    List<Paquete> findByTituloContainingIgnoreCaseAndActivo(String titulo, Boolean activo);

    /**
     * Contar paquetes por destino
     */
    Long countByDestinoId(Long destinoId);

    /**
     * Contar paquetes activos por destino
     */
    Long countByDestinoIdAndActivo(Long destinoId, Boolean activo);

    /**
     * Verificar si existe un paquete por título
     */
    boolean existsByTitulo(String titulo);
}
