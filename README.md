# VetTurno

La agenda digital de **Veterinaria Huellitas**.

## Historia

Doña Marta abrió Veterinaria Huellitas hace seis años. La atiende junto al doctor Andrés y a Paula, la recepcionista.
La agenda vive entre un cuaderno y conversaciones de WhatsApp: a veces se reservan dos consultas para el mismo
veterinario a la misma hora, se escribe mal el nombre de una mascota o se pierde el teléfono de su responsable.

VetTurno es una API REST que permite registrar responsables, mascotas y veterinarios, agendar citas sin cruces de
horario y consultar la agenda con información clara y persistente.

### Personas usuarias

| Persona | Rol | Qué hace en VetTurno |
|---|---|---|
| Paula, recepcionista | `USER` | Registra responsables y mascotas, agenda citas y consulta la agenda |
| Doña Marta, administradora | `ADMIN` | Todo lo anterior y además registra veterinarios |
| Doctor Andrés, veterinario | — | Consulta sus citas ordenadas (no inicia sesión en este MVP) |

### Alcance del MVP

| Incluye | Fuera de alcance |
|---|---|
| Registro y login con roles USER y ADMIN | Historia clínica, diagnósticos o fórmulas |
| Responsables, mascotas, veterinarios y citas | Pagos, facturación, inventario o tienda |
| Prevención de horarios duplicados por veterinario | Recordatorios por correo o WhatsApp |
| Consulta de citas por veterinario | Interfaz web o aplicación móvil |
| Validación, errores, Swagger, MySQL | Despliegue cloud y Docker como requisito |

## Tecnologías

Java 17 · Spring Boot 4 · Maven · MySQL · JPA/Hibernate · Spring Security · JWT (jjwt) · Bean Validation · Swagger/OpenAPI (springdoc)

## Arquitectura

Paquete base `com.example.vetTurno.vetTurno`:

| Paquete | Responsabilidad |
|---|---|
| `controller` | Recibe la petición HTTP, activa `@Valid` y elige el estado (200/201). No accede a repositorios |
| `service` | Reglas del negocio: resolver relaciones por id, fecha futura, horario libre, registro con BCrypt |
| `repository` | Acceso a MySQL con `JpaRepository` y consultas derivadas |
| `model` | Entidades JPA (lo que se guarda en MySQL) |
| `dto` | Contratos de entrada (`...Request`) y salida (`...DTO`). La API nunca devuelve entidades |
| `security` | JWT, filtro Bearer, carga del usuario y reglas de acceso por rol |
| `exception` | `ApiError` y manejador global de errores |
| `config` | Configuración de OpenAPI/Swagger |

Flujo de una petición: `Controller → Service → Repository → MySQL`, y la respuesta vuelve convertida en DTO.

## Modelo de datos

| Tabla | Campos | Relación |
|---|---|---|
| `propietarios` | id, nombre, telefono, email | — |
| `mascotas` | id, nombre, especie, raza, propietario_id | Muchas mascotas → un propietario |
| `veterinarios` | id, nombre, especialidad | — |
| `citas` | id, fecha_hora, motivo, mascota_id, veterinario_id | Muchas citas → una mascota y un veterinario |
| `usuarios` | id, email (único), password (BCrypt), rol (`USER`/`ADMIN` como texto) | — |

Las llaves foráneas quedan en el lado "muchos" (`mascotas` y `citas`). Las relaciones son unidireccionales
(`@ManyToOne` en `Mascota` y `Cita`): en el MVP ningún caso de uso necesita navegar de un propietario a sus mascotas,
y así se evitan colecciones innecesarias y ciclos JSON.

## Configuración

La conexión y el secreto JWT se leen de variables de entorno. Si no existen, se usan valores locales de desarrollo
definidos en `application.properties`.

| Variable | Uso | Valor local por defecto                              |
|---|---|------------------------------------------------------|
| `DB_URL` | URL JDBC de MySQL | `jdbc:mysql://localhost:3306/vetturno`               |
| `DB_USER` | Usuario de MySQL | `-`                                                  |
| `DB_PASSWORD` | Contraseña de MySQL | `-`                                                  |
| `JWT_SECRET` | Clave para firmar los JWT (mínimo 32 caracteres) | clave de desarrollo; **cámbiala fuera de tu equipo** |
| `JWT_EXPIRATION_MS` | Vigencia del token | `3600000` (1 hora)                                   |

## Cómo ejecutar

1. Tener MySQL encendido (por ejemplo desde XAMPP) y crear una base vacía:
   ```sql
   CREATE DATABASE vetturno;
   ```
2. Si tu MySQL tiene contraseña, definir `DB_PASSWORD` (en IntelliJ: *Run → Edit Configurations → Environment variables*).
3. Ejecutar la aplicación:
   ```bash
   .\mvnw.cmd spring-boot:run   # Windows
   ./mvnw spring-boot:run       # macOS/Linux
   ```
4. Hibernate crea las tablas automáticamente (`ddl-auto=update`).
5. Abrir Swagger: <http://localhost:8080/swagger-ui.html>

## Roles y seguridad

- `POST /api/auth/register` y `POST /api/auth/login` son públicos. Todo lo demás exige `Authorization: Bearer <token>`.
- El registro **siempre** crea el usuario como `USER`, aunque el cliente envíe otro rol.
- Solo `ADMIN` puede registrar veterinarios (`POST /api/veterinarios`); un `USER` recibe **403**.
- La API es *stateless*: no hay sesiones en el servidor, cada petición lleva su token.

**Habilitar el primer ADMIN.** Registrar a la persona normalmente y luego, en la base de datos:
```sql
UPDATE usuarios SET rol = 'ADMIN' WHERE email = 'marta@huellitas.com';
```
Después debe **iniciar sesión de nuevo** para recibir un token con el rol nuevo.

## Endpoints

| Método | Ruta | Acceso | Resultado |
|---|---|---|---|
| POST | `/api/auth/register` | Público | 200 + token (rol USER) |
| POST | `/api/auth/login` | Público | 200 + JWT |
| POST | `/api/propietarios` | USER / ADMIN | 201 |
| GET | `/api/propietarios` | USER / ADMIN | 200 |
| POST | `/api/mascotas` | USER / ADMIN | 201 (el propietario debe existir) |
| GET | `/api/mascotas` | USER / ADMIN | 200 |
| POST | `/api/veterinarios` | **ADMIN** | 201 (USER → 403) |
| GET | `/api/veterinarios` | USER / ADMIN | 200 |
| POST | `/api/citas` | USER / ADMIN | 201 (fecha futura y horario libre) |
| GET | `/api/citas` | USER / ADMIN | 200, ordenadas por fecha |
| GET | `/api/citas/veterinario/{id}` | USER / ADMIN | 200, solo las de ese veterinario |

Las fechas se envían en formato ISO sin zona horaria: `"2026-10-15T10:00:00"`.

## Orden recomendado para probar

1. Registrar a Paula (`paula@huellitas.com`) y a Doña Marta (`marta@huellitas.com`).
2. Convertir a Marta en ADMIN con el `UPDATE` de arriba e iniciar sesión con ella.
3. En Swagger, botón **Authorize**, pegar el token.
4. Con el token de Marta: crear un veterinario.
5. Crear un propietario, luego una mascota con ese `propietarioId`.
6. Agendar una cita con `mascotaId` y `veterinarioId`.
7. Consultar la agenda completa y la agenda por veterinario.
8. Reiniciar la aplicación y repetir las consultas: los datos siguen en MySQL.

La carpeta [`bruno/`](bruno/) contiene una colección de [Bruno](https://www.usebruno.com/) con todos los endpoints y los
casos de la matriz. Se abre con *Open Collection* y el entorno **Local**.

## Formato de errores

Todos los errores responden con la misma estructura (`ApiError`):

```json
{
  "status": 400,
  "mensaje": "Los datos enviados no son válidos",
  "errores": {
    "email": "El email no es válido",
    "password": "La contraseña debe tener al menos 6 caracteres"
  },
  "timestamp": "2026-09-29T22:44:44.808"
}
```

| Estado | Cuándo |
|---|---|
| 200 | Consulta, registro o login correctos |
| 201 | Creación correcta |
| 400 | Datos inválidos, referencia inexistente, fecha pasada, horario ocupado, email repetido o JSON mal formado |
| 401 | Sin token, token inválido o vencido, credenciales incorrectas |
| 403 | Autenticado pero sin el rol necesario |
| 500 | Error inesperado, con mensaje genérico y sin detalles internos |



Las capturas se guardan en [`docs/evidencias/`](docs/evidencias/), donde está la convención de nombres.


## Uso de IA

Se usó un asistente de IA (Claude) para revisar el modelo de relaciones, diagnosticar errores de conexión con MySQL, cada sugerencia se comprobó 
revisando detalladamente la respuesta y compilando la aplicación, revisando las tablas y llaves foráneas en la base de datos y corriendo partes de este README.
