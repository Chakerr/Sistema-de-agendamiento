# Versión 1.1

**Fecha:** 23/10/2024

## Historia de Usuario 4.1 – Validación de reserva con el carnet

### Descripción

Cuando el estudiante o grupo de estudiantes vayan a entrar al laboratorio para realizar la práctica reservada, se verificará la asistencia mediante el escaneo del carnet estudiantil.

**Como:** USUARIO (ESTUDIANTE)  
**Quiero:** Confirmar mi asistencia a la reserva previamente agendada en la plataforma.  
**Para:** Validar de manera segura la asistencia de los integrantes de la reserva a partir del usuario que realizó la reserva.

---

### Criterio de aceptación 1

El usuario debe llevar el carnet consigo a la hora de pasarlo por el lector.

**Dado que:** A partir de la lectura del carnet se confirmará la reserva.  
**Cuando:** El usuario que realizó la reserva vaya a entrar al laboratorio.  
**Entonces:** Se validarán las credenciales del carnet. Si el identificador del carnet coincide con el registro del titular, se actualizará en la base de datos el número de visitas tanto del titular como de los integrantes y el estado de la reserva.

En caso de que el código leído coincida con el registro en la base de datos, se enviará un mensaje de confirmación de asistencia al correo del usuario. Si no coincide, se enviará un mensaje al laboratorista indicando que el carnet escaneado no corresponde al registro del usuario que realizó la reserva.

---

### Criterio de aceptación 2

El usuario que realizó la reserva debe estar presente en la práctica.

**Dado que:** La confirmación de la asistencia se hace a partir de la presencia del usuario que efectuó la reserva.  
**Cuando:** El usuario valide su asistencia a la práctica en el laboratorio.  
**Entonces:** Se realizará la búsqueda en la base de datos y se actualizarán los registros previamente mencionados.

---

### Observación

Si el código arrojado al escanear el carnet no coincide con el registro en la base de datos, se le notificará al laboratorista. Las posibles razones pueden ser:

- El chip del carnet está dañado.
- El usuario presentó un carnet falso, es decir, no es un estudiante de la universidad.
- El lector RFID está dañado.

El orden de estas posibilidades va de lo más probable a lo menos probable. Es decir, lo último que se verificará será el estado del lector. Por lo tanto, el laboratorista deberá dirigirse al laboratorio para revisar la situación.
