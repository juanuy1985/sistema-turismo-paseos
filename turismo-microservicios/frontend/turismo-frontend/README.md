# Turismo Frontend (Angular)

Frontend Angular del sistema de turismo para consumir exclusivamente el API Gateway.

## Requisitos

- Node.js 18+
- npm 9+
- API Gateway ejecutándose en `http://localhost:8080`

## Instalación

```bash
cd /home/runner/work/sistema-turismo-paseos/sistema-turismo-paseos/turismo-microservicios/frontend/turismo-frontend
npm install
```

## Ejecución

```bash
npm start
```

La app queda en `http://localhost:4200`.

> El proyecto usa `proxy.conf.json` para redirigir `/api/*` al Gateway (`http://localhost:8080`).

## Scripts útiles

- `npm start` → servidor de desarrollo
- `npm run build` → build de producción
- `npm test` → pruebas unitarias (Karma)

## Endpoints consumidos (vía API Gateway)

- `GET /api/paquetes`
- `GET /api/paquetes/{id}`
- `POST /api/clientes`
- `GET /api/clientes/documento/{numeroDocumento}`
- `POST /api/reservas`
- `GET /api/reservas/codigo/{codigoReserva}`
- `GET /api/reservas/cliente/{clienteId}`
- `POST /api/pagos`
- `GET /api/pagos/codigo-reserva/{codigoReserva}`

## Flujo UI implementado

1. Listar paquetes
2. Registrar cliente
3. Crear reserva
4. Consultar reserva (por código o cliente)
5. Procesar pago
6. Ver confirmaciones/notificaciones simuladas

## Estructura principal

- `src/app/core` → layout e interceptor global de errores HTTP
- `src/app/shared` → recursos compartidos
- `src/app/features` → lógica de flujo de notificaciones
- `src/app/services` → servicios HTTP por dominio
- `src/app/models` → modelos TypeScript
- `src/app/pages` → pantallas de negocio
