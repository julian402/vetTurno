# VetTurno

La agenda digital de Veterinaria Huellitas

## Tecnologías

Java 17 · Spring Boot · Maven · MySQL · JPA/Hibernate · JWT · Swagger/OpenAPI

## Modelo de datos

| Tabla          | Campos                                             | Relación                                   |
|----------------|----------------------------------------------------|--------------------------------------------|
| `propietarios` | id, nombre, telefono, email                        | —                                          |
| `mascotas`     | id, nombre, especie, raza, propietario_id          | Muchas mascotas → un propietario           |
| `veterinarios` | id, nombre, especialidad                           | —                                          |
| `citas`        | id, fecha_hora, motivo, mascota_id, veterinario_id | Muchas citas → una mascota y un veterinario |


## Cómo ejecutar

1. Crear en MySQL una base vacía:
   ```sql
   CREATE DATABASE vetturno;
   ```
2. Configurar las variables de entorno de la conexión (o usar los valores por defecto de `application.properties`):
   `DB_URL`, `DB_USER`, `DB_PASSWORD`.
3. Ejecutar la aplicación:
   ```bash
   .\mvnw.cmd spring-boot:run   # Windows
   ./mvnw spring-boot:run       # macOS/Linux
   ```
4. Hibernate crea las tablas automáticamente (`ddl-auto=update`).
