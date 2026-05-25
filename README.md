# sistema-turismo-paseos

Sistema turístico para paseos - Práctica laboratorio 01.

## Flujo para levantar backend completo

> Carpeta base del backend: `turismo-microservicios/backend`

### 1) Levantar infraestructura (Docker)

```bash
cd /home/runner/work/sistema-turismo-paseos/sistema-turismo-paseos/turismo-microservicios/docker
cp .env.example .env 2>/dev/null || true
```

Asegura en `.env` al menos estas variables:

```env
PAGOS_DB_PASSWORD=pagos_password_seguro
RESERVAS_DB_PASSWORD=reservas_password_seguro
```

Inicia PostgreSQL + RabbitMQ:

```bash
docker compose up -d
```

### 2) Levantar Eureka Server

En una terminal:

```bash
cd /home/runner/work/sistema-turismo-paseos/sistema-turismo-paseos/turismo-microservicios/backend/eureka-server
mvn spring-boot:run
```

Verifica: `http://localhost:8761`

### 3) Levantar microservicios

En terminales separadas (esperar que Eureka esté arriba):

```bash
cd /home/runner/work/sistema-turismo-paseos/sistema-turismo-paseos/turismo-microservicios/backend/ms-paquetes && mvn spring-boot:run
cd /home/runner/work/sistema-turismo-paseos/sistema-turismo-paseos/turismo-microservicios/backend/ms-usuarios && mvn spring-boot:run
cd /home/runner/work/sistema-turismo-paseos/sistema-turismo-paseos/turismo-microservicios/backend/ms-reservas && mvn spring-boot:run
cd /home/runner/work/sistema-turismo-paseos/sistema-turismo-paseos/turismo-microservicios/backend/ms-pagos && mvn spring-boot:run
cd /home/runner/work/sistema-turismo-paseos/sistema-turismo-paseos/turismo-microservicios/backend/ms-notificaciones && mvn spring-boot:run
```

### 4) Levantar API Gateway

En otra terminal:

```bash
cd /home/runner/work/sistema-turismo-paseos/sistema-turismo-paseos/turismo-microservicios/backend/api-gateway
mvn spring-boot:run
```

Gateway disponible en: `http://localhost:8080`

## Probar flujo completo con Postman

Importa la colección:

`/home/runner/work/sistema-turismo-paseos/sistema-turismo-paseos/postman/flujo-completo-backend.postman_collection.json`

Orden sugerido de ejecución en Postman:

1. **Listar paquetes** (`GET /api/paquetes`)
2. **Crear cliente** (`POST /api/clientes`)
3. **Crear reserva** (`POST /api/reservas`)
4. **Procesar pago** (`POST /api/pagos`)
5. **Verificar notificaciones** (consulta por código y validación de logs)

La colección incluye ejemplos de request/response y variables encadenadas (`clienteId`, `paqueteId`, `reservaId`, `codigoReserva`, `montoTotal`, `moneda`).

## Verificar logs de notificación

Después de crear reserva y procesar pago, valida en la consola de `ms-notificaciones` (o contenedor) que aparezcan logs similares a:

- `Simulación notificación reserva creada: ...`
- `Simulación notificación pago confirmado: ...`

Si `ms-notificaciones` corre en Docker, puedes usar:

```bash
docker logs -f <contenedor-ms-notificaciones>
```
