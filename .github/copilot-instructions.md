# Protocolo Organic Driven Development (ODD) - Java 21 & Spring Boot

Aplica este flujo por defecto en cada solicitud. **NUNCA ejecutes `git commit`, `git push` ni crees ramas**; el control de versiones lo maneja exclusivamente el desarrollador.

1. **Autorizar (`Modo Lectura` vs `Cambio`):**
   - Si la solicitud es una duda, explicación, investigación o propuesta, mantente en **solo lectura** (no modifiques ni crees archivos).

2. **Explorar e Incertidumbre:**
   - Revisa primero el código existente en `src/main/java` y los tests en `src/test/java`.
   - Si hay dudas reales de negocio o alcance, investiga en el código o haz **1 pregunta puntual** al usuario y espera respuesta; no inventes requisitos.
   - Si detectas un supuesto de alto riesgo no probado (ej. seguridad o pérdida de datos), cuestiónalo una sola vez antes de avanzar.

3. **Clasificar tamaño (El corazón de ODD):**
   - **Cambio pequeño (Flujo ligero):** El alcance es claro, el riesgo está contenido y el trabajo se entiende viendo la solicitud original + `git diff`. **NO** crees documentos en `odd/tasks/`.
   - **Cambio sustancial:** Múltiples reglas, pasos o estados que no se podrían retomar fácilmente viendo solo la solicitud + `git diff`.

4. **Documentar antes de escribir código (solo cambio sustancial):**
   Antes de editar cualquier archivo `.java`, crea `odd/tasks/<feature>.md` e informa en una sola línea qué archivo creaste y cuántas tareas contiene. Usa estrictamente este orden:
   - Encabezado corto con el nombre del feature y el runner detectado (`mvn test`).
   - `## Specs`: Reglas numeradas (`S1`, `S2`...) citando textualmente mensajes de error, códigos HTTP y ejemplos pedidos, más criterios de aceptación.
   - `## Tasks`: Una línea por tarea (`- [ ] T1 (S1): descripción | evidencia de prueba`).
   - `## Log`: `L1` con la solicitud original exacta; agrega debajo ajustes, hallazgos o decisiones posteriores.

5. **Implementar tarea por tarea (Ciclo Test-First):**
   - Detecta y usa el runner del proyecto (`mvn test`). Nunca inventes un runner.
   - **RED -> GREEN -> Refactor:** Escribe primero el test con JUnit 5 / `MockMvc` por cada regla solicitada y ejecuta `mvn test` para observar el fallo (RED). Implementa en Java 21 hasta pasar en verde (GREEN) y refactoriza manteniendo legibilidad.
   - Valida **cero regresiones** comprobando que todos los tests previos sigan pasando.
   - Mantén las unidades de cambio pequeñas y cohesivas (~400 líneas modificadas como guía orientativa de tamaño saludable, sin recortar tests ni comentarios).
   - Marca `[x]` en `## Tasks` registrando qué test validó la tarea únicamente después de observar `mvn test` exitoso en consola.

6. **Cambios de requisitos y Reanudación:**
   - **Si cambia un requisito:** Reescribe únicamente la `Spec` afectada en `odd/tasks/<feature>.md`, reabre o agrega solo la `Task` correspondiente en `## Tasks`, anota el motivo en `## Log` y preserva intacto el resto del trabajo completado.
   - **Al retomar un feature pausado:** Lee primero `odd/tasks/<feature>.md` hasta `## Log`, concilia el estado real con el código actual y `git diff`, y retoma desde la siguiente tarea pendiente.

7. **Cierre:**
   - Cierra reportando el resultado verificado, el resumen de tests ejecutados y cualquier chequeo fallido o pendiente.