# Evidencias de VetTurno

Capturas de pantalla que respaldan cada parte del taller y la matriz de pruebas del [README principal](../../README.md).

## Convención de nombres

`parteN-descripcion-corta.png` para las partes del taller y `pruebaNN-descripcion.png` para la matriz.

| Archivo sugerido | Qué muestra |
|---|---|
| `parte1-servidor-iniciado.png` | Consola con `Started VetTurnoApplication` |
| `parte1-arbol-paquetes.png` | Árbol de paquetes del proyecto |
| `parte2-sql-generado.png` | SQL `create table` / `foreign key` en la consola |
| `parte2-diagrama-er.png` | Diagrama de tablas y llaves foráneas |
| `parte5-hash-password.png` | Tabla `usuarios` con la contraseña en BCrypt |
| `parte7-swagger-authorize.png` | Swagger con la información de VetTurno y el botón Authorize |
| `prueba01-app-inicia.png` … `prueba15-reinicio-persistencia.png` | Un archivo por cada caso de la matriz |

## Recomendaciones

- Oculta parcialmente los tokens JWT antes de capturar (por ejemplo `eyJhbGciOi...xyz`).
- No muestres contraseñas reales ni el secreto JWT.
- Acompaña cada captura con una descripción en la tabla de la matriz del README, para que no dependa solo de la imagen.
