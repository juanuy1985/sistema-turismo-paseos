# Backend - Microservicios Spring Boot

## Descripción

Este directorio contiene todos los microservicios del sistema de turismo, construidos con Spring Boot.

## Estructura de Microservicios

### 1. **eureka-server**
Servidor de descubrimiento de servicios (Service Registry).
- Puerto recomendado: 8761
- Responsabilidad: Registro y descubrimiento automático de microservicios
- Documentación: [Spring Cloud Netflix Eureka](https://spring.io/projects/spring-cloud-netflix)

### 2. **api-gateway**
Puerta de entrada a todos los microservicios (API Gateway).
- Puerto recomendado: 8080
- Responsabilidad: Enrutamiento, autenticación, rate limiting
- Tecnología: Spring Cloud Gateway

### 3. **ms-paquetes**
Microservicio de gestión de paquetes turísticos.
- Puerto recomendado: 8081
- Responsabilidades:
  - CRUD de paquetes turísticos
  - Gestión de destinos y actividades
  - Información de precios y disponibilidad

### 4. **ms-usuarios**
Microservicio de gestión de usuarios.
- Puerto recomendado: 8082
- Estado: Inicializado en `backend/ms-usuarios`
- Responsabilidades:
  - Autenticación y autorización
  - Gestión de perfiles de usuario
  - Roles y permisos

### 5. **ms-reservas**
Microservicio de gestión de reservas.
- Puerto recomendado: 8083
- Responsabilidades:
  - Creación y gestión de reservas
  - Control de disponibilidad
  - Historial de reservas
  - Endpoint de ejemplo reactivo solicitado por laboratorio: `GET /api/reservas/reactive/{id}` (WebFlux/Mono)

### 6. **ms-pagos**
Microservicio de procesamiento de pagos.
- Puerto recomendado: 8084
- Responsabilidades:
  - Procesamiento de pagos
  - Gestión de transacciones
  - Integración con pasarelas de pago

### 7. **ms-notificaciones**
Microservicio de notificaciones.
- Puerto recomendado: 8085
- Responsabilidades:
  - Envío de emails
  - Notificaciones SMS
  - Notificaciones push

## Tecnologías Comunes

- **Framework**: Spring Boot 2.7+ / 3.x
- **Cloud**: Spring Cloud
- **Base de Datos**: MySQL / PostgreSQL / MongoDB (según necesidad)
- **Mensajería**: RabbitMQ / Apache Kafka (para comunicación async)
- **Build**: Maven / Gradle
- **Documentación API**: Swagger / Springdoc OpenAPI

## Configuración General

### Dependencias Comunes (pom.xml / build.gradle)

```xml
<!-- Spring Boot -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- Spring Cloud -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>

<!-- JPA/Hibernate -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- Swagger/OpenAPI -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
</dependency>
```

## Pasos para Crear un Microservicio

1. Navegar a la carpeta del microservicio
2. Ejecutar: `spring init --type maven --language java --name ms-paquetes ms-paquetes`
3. O usar Spring Boot CLI / IDE (IntelliJ, Eclipse, VS Code)
4. Agregar dependencias necesarias
5. Crear la estructura básica:
   ```
   src/
   ├── main/
   │   ├── java/com/turismo/
   │   │   ├── Application.java
   │   │   ├── controller/
   │   │   ├── service/
   │   │   ├── repository/
   │   │   ├── model/
   │   │   └── config/
   │   └── resources/
   │       └── application.yml
   └── test/
   ```

## Configuración de application.yml

```yaml
spring:
  application:
    name: ms-paquetes
  jpa:
    hibernate:
      ddl-auto: update
  datasource:
    url: jdbc:mysql://localhost:3306/turismo_paquetes
    username: root
    password: password
    driver-class-name: com.mysql.cj.jdbc.Driver

server:
  port: 8081
  servlet:
    context-path: /api/paquetes

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka
  instance:
    instance-id: ${spring.application.name}:${random.value}
```

## Comunicación entre Microservicios

- **Síncrona**: RestTemplate, WebClient, Feign Client
- **Asíncrona**: RabbitMQ, Apache Kafka, Pub/Sub

## Testing

- **Unit Tests**: JUnit 5, Mockito
- **Integration Tests**: TestContainers
- **API Tests**: REST Assured

## Despliegue

Ver instrucciones en `/docker` para desplegar con Docker Compose.

## Documentación Adicional

- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [Spring Cloud Docs](https://spring.io/projects/spring-cloud)
- [Building Microservices with Spring Boot](https://spring.io/guides/gs/multi-module/)
