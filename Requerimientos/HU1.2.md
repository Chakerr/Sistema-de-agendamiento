# Versión 1.2

**Fecha:** 23/10/2024

## Historia de Usuario 1.2 – Reserva de laboratorio

### Descripción

Al haber ingresado a la plataforma, el usuario desea llevar a cabo la reserva del laboratorio.

**Como:** USUARIO (ESTUDIANTE)  
**Quiero:** Reservar una sala de laboratorio después de haber llenado los campos de fecha de asistencia, hora de ingreso, horas de laboratorio, carrera y total de acompañantes, seguido de verificaciones del sistema en términos de capacidad, inventario y materias.  
**Para:** Efectuar una determinada práctica en una hora disponible y poder usar el inventario adecuado para la misma.

---

### Criterio de aceptación 1

La práctica a realizar debe ser acorde a una materia que debe tener el correspondiente inventario.

**Dado que:** Las distintas prácticas difieren en cuanto a conceptos y, por lo tanto, en cuanto a inventario y salón según la necesidad del usuario.  
**Cuando:** El usuario esté especificando la materia de la práctica que va a llevar a cabo.  
**Entonces:** Se buscará en la base de datos la materia y el correspondiente laboratorio donde se llevan a cabo dichas prácticas, para así saber cuál es el área de interés del usuario para llevar a cabo la reserva.

---

### Criterio de aceptación 2

El usuario debe llenar los campos de fecha de ingreso, hora de ingreso, horas a realizar y total de acompañantes.

**Dado que:** Indicar la hora de llegada y la duración son aspectos relevantes en el proceso de reserva para verificar la disponibilidad de inventario y de salón.  
**Cuando:** El usuario esté especificando los detalles de su reserva.  
**Entonces:** Se verificará en la base de datos que en esa hora no haya conflictos con alguna clase o reserva, que la capacidad del laboratorio no sea superada, y que el inventario necesario esté disponible según la materia especificada en el criterio 1.

Si no existe ningún cruce en términos de laboratorio, capacidad o inventario, se hará efectiva la reserva. De lo contrario, se le informará al usuario con el mensaje “No es posible agendar la práctica en el horario solicitado”. El usuario puede modificar la hora y la fecha para intentar nuevamente.

---

### Observación

Conforme avance el proyecto, se decidirá si, en caso de no poder hacerse efectiva la reserva por motivos de disponibilidad horaria, capacidad del laboratorio o inventario, se dará un mensaje al usuario indicando la razón, mencionando a qué hora termina la clase o práctica, o cuándo estará disponible el material, para mejorar la experiencia en la plataforma.

Inicialmente, en las primeras entregas, estos mensajes no aparecerán, priorizando el correcto funcionamiento y prueba de la plataforma en diversas situaciones. Posteriormente, cuando estos aspectos hayan sido validados, se buscará cómo hacer la herramienta más completa y dinámica.
