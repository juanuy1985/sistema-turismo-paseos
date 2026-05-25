package com.turismo.config;

import com.turismo.model.Destino;
import com.turismo.model.Paquete;
import com.turismo.model.TipoPaseo;
import com.turismo.repository.DestinoRepository;
import com.turismo.repository.PaqueteRepository;
import com.turismo.repository.TipoPaseoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitialDataLoader implements ApplicationRunner {

    private final DestinoRepository destinoRepository;
    private final TipoPaseoRepository tipoPaseoRepository;
    private final PaqueteRepository paqueteRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        Map<String, Destino> destinos = cargarDestinos();
        Map<String, TipoPaseo> tiposPaseo = cargarTiposPaseo();
        cargarPaquetes(destinos, tiposPaseo);
    }

    private Map<String, Destino> cargarDestinos() {
        Map<String, Destino> destinos = new HashMap<>();
        destinos.put("Machu Picchu", obtenerOCrearDestino("Machu Picchu", "Perú", "Cusco"));
        destinos.put("Paracas", obtenerOCrearDestino("Paracas", "Perú", "Ica"));
        destinos.put("Laguna 69", obtenerOCrearDestino("Laguna 69", "Perú", "Ancash"));
        destinos.put("Cusco", obtenerOCrearDestino("Cusco", "Perú", "Cusco"));
        destinos.put("Arequipa", obtenerOCrearDestino("Arequipa", "Perú", "Arequipa"));
        destinos.put("Iquitos", obtenerOCrearDestino("Iquitos", "Perú", "Loreto"));
        destinos.put("Valle Sagrado", obtenerOCrearDestino("Valle Sagrado", "Perú", "Cusco"));
        return destinos;
    }

    private Map<String, TipoPaseo> cargarTiposPaseo() {
        Map<String, TipoPaseo> tiposPaseo = new HashMap<>();
        tiposPaseo.put("INDIVIDUAL", obtenerOCrearTipoPaseo("INDIVIDUAL"));
        tiposPaseo.put("GRUPAL", obtenerOCrearTipoPaseo("GRUPAL"));
        tiposPaseo.put("FAMILIAR", obtenerOCrearTipoPaseo("FAMILIAR"));
        tiposPaseo.put("EMPRESARIAL", obtenerOCrearTipoPaseo("EMPRESARIAL"));
        return tiposPaseo;
    }

    private void cargarPaquetes(Map<String, Destino> destinos, Map<String, TipoPaseo> tiposPaseo) {
        crearPaqueteSiNoExiste(
                "Machu Picchu Aventura",
                "Experiencia completa a Machu Picchu con guiado especializado.",
                destinos.get("Machu Picchu"),
                tiposPaseo.get("INDIVIDUAL"),
                new BigDecimal("499.00"),
                "USD",
                3,
                20,
                true
        );
        crearPaqueteSiNoExiste(
                "Paracas y Ballestas Full Day",
                "Recorrido de día completo por Paracas e Islas Ballestas.",
                destinos.get("Paracas"),
                tiposPaseo.get("GRUPAL"),
                new BigDecimal("320.00"),
                "PEN",
                1,
                30,
                true
        );
        crearPaqueteSiNoExiste(
                "Trekking Laguna 69",
                "Caminata guiada de alta montaña a Laguna 69.",
                destinos.get("Laguna 69"),
                tiposPaseo.get("GRUPAL"),
                new BigDecimal("210.00"),
                "PEN",
                2,
                16,
                true
        );
        crearPaqueteSiNoExiste(
                "City Tour Cusco Premium",
                "Tour urbano en Cusco con experiencias culturales y gastronómicas.",
                destinos.get("Cusco"),
                tiposPaseo.get("EMPRESARIAL"),
                new BigDecimal("380.00"),
                "USD",
                2,
                12,
                true
        );
        crearPaqueteSiNoExiste(
                "Arequipa Tradicional",
                "Visita a los principales atractivos históricos y culturales de Arequipa.",
                destinos.get("Arequipa"),
                tiposPaseo.get("FAMILIAR"),
                new BigDecimal("280.00"),
                "PEN",
                2,
                18,
                true
        );
        crearPaqueteSiNoExiste(
                "Aventura Amazónica Iquitos",
                "Exploración por la selva amazónica con actividades de naturaleza.",
                destinos.get("Iquitos"),
                tiposPaseo.get("INDIVIDUAL"),
                new BigDecimal("620.00"),
                "USD",
                4,
                10,
                true
        );
        crearPaqueteSiNoExiste(
                "Valle Sagrado Familiar",
                "Circuito familiar por los principales sitios del Valle Sagrado.",
                destinos.get("Valle Sagrado"),
                tiposPaseo.get("FAMILIAR"),
                new BigDecimal("450.00"),
                "PEN",
                3,
                24,
                true
        );
    }

    private Destino obtenerOCrearDestino(String nombre, String pais, String region) {
        return destinoRepository.findByNombre(nombre)
                .orElseGet(() -> {
                    Destino destino = new Destino();
                    destino.setNombre(nombre);
                    destino.setPais(pais);
                    destino.setRegion(region);
                    destino.setActivo(true);
                    log.info("Creando destino inicial: {}", nombre);
                    return destinoRepository.save(destino);
                });
    }

    private TipoPaseo obtenerOCrearTipoPaseo(String nombre) {
        return tipoPaseoRepository.findByNombre(nombre)
                .orElseGet(() -> {
                    TipoPaseo tipoPaseo = new TipoPaseo();
                    tipoPaseo.setNombre(nombre);
                    tipoPaseo.setActivo(true);
                    log.info("Creando tipo de paseo inicial: {}", nombre);
                    return tipoPaseoRepository.save(tipoPaseo);
                });
    }

    private void crearPaqueteSiNoExiste(
            String titulo,
            String descripcion,
            Destino destino,
            TipoPaseo tipoPaseo,
            BigDecimal precio,
            String moneda,
            Integer duracionDias,
            Integer cuposTotales,
            Boolean activo
    ) {
        if (paqueteRepository.existsByTitulo(titulo)) {
            log.debug("Paquete inicial ya existe, se omite creación: {}", titulo);
            return;
        }

        Paquete paquete = new Paquete();
        paquete.setTitulo(titulo);
        paquete.setDescripcion(descripcion);
        paquete.setDestino(destino);
        paquete.setTipoPaseo(tipoPaseo);
        paquete.setPrecio(precio);
        paquete.setMoneda(moneda);
        paquete.setDuracionDias(duracionDias);
        paquete.setCuposTotales(cuposTotales);
        paquete.setCuposDisponibles(cuposTotales);
        paquete.setActivo(activo);

        log.info("Creando paquete inicial: {}", titulo);
        paqueteRepository.save(paquete);
    }
}
