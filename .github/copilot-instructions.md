# Protocolo Organic Driven Development (ODD) - Java 21 & Spring Boot

Aplica este flujo por defecto en cada solicitud:

1. **Autorizar:** Si la solicitud es una consulta o explicación, mantente en solo lectura (no modifiques archivos).
2. **Explorar:** Revisa primero las clases en `src/main/java` y los tests en `src/test/java`. Solo haz preguntas si hay una regla de negocio ambigua.
3. **Clasificar tamaño:**
    - **Pequeño:** Alcance directo, riesgo contenido y fácil de retomar viendo la solicitud más `git diff`. NO crees archivos en `odd/tasks/`.
    - **Sustancial:** Múltiples reglas de negocio, validaciones o estados que no se deducen de un `git diff` a medio terminar.
4. **Documentar antes de escribir código (solo trabajo sustancial):**
   Antes de modificar cualquier `.java`, crea `odd/tasks/<feature>.md` e informa en una sola línea qué archivo creaste y cuántas tareas contiene. Respeta este orden exacto:
    - Encabezado corto con el nombre del feature y el runner de pruebas: `mvn test`.
    - `## Specs`: Reglas numeradas (`S1`, `S2`...) citando textualmente mensajes de error, códigos HTTP y ejemplos solicitados.
    - `## Tasks`: Una línea por tarea (`- [ ] T1 (S1): descripción | evidencia`).
    - `## Log`: `L1` con la solicitud original exacta.
5. **Implementar tarea por tarea (Test-First):**
    - Escribe primero el test con JUnit 5 / `MockMvc` y ejecuta `mvn test` para comprobar que falla (RED).
    - Implementa en Java 21 y vuelve a ejecutar `mvn test` hasta pasar en verde (GREEN), comprobando que los tests previos sigan pasando sin regresiones.
    - Marca `[x]` en `## Tasks` únicamente después de observar `mvn test` exitoso en consola.
6. **Cerrar:** Resume el resultado verificado, los tests ejecutados y sugiere el mensaje de Conventional Commit.