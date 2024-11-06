# Versión 1.1

**Fecha:** 23/10/2024

## Historia de Usuario 2.1 – Confirmación de reserva del laboratorio

### Descripción

El usuario recibirá una confirmación de la reserva que acaba de hacer, previa validación de todas las condiciones necesarias para apartar una sala.

**Como:** USUARIO (ESTUDIANTE)  
**Quiero:** Recibir una confirmación de la reserva de laboratorio exitosa, enviada por correo electrónico, que corresponde al usuario de la plataforma.  
**Para:** Efectuar una práctica que garantice seguridad, transparencia y comunicación efectiva con el usuario.

---

### Criterio de aceptación 1

La reserva debe cumplir con todos los criterios de aceptación de la historia de usuario 1.2.

**Dado que:** La historia de usuario anterior establecía las condiciones y requerimientos para realizar una reserva óptima en términos de horario, aforo e instrumentación.  
**Cuando:** El usuario complete todos los campos del proceso de reserva y ninguna validación de disponibilidad presente inconvenientes.  
**Entonces:** Se hará efectiva la reserva, añadiéndola a la base de datos con la información pertinente a la práctica.

---

### Criterio de aceptación 2

El usuario debe revisar su correo electrónico un tiempo después de la reserva.

**Dado que:** La confirmación será enviada a través del correo electrónico proporcionado por el usuario, que también es utilizado como credencial de inicio de sesión.  
**Cuando:** El sistema haya añadido exitosamente la reserva del usuario a la base de datos.  
**Entonces:** La plataforma enviará automáticamente un correo notificando al usuario los detalles de su reserva, incluyendo la hora, fecha y práctica diligenciadas durante el proceso. Esto permitirá que el usuario tenga presente el evento y evidencie el correcto funcionamiento de la plataforma.

---

### Observación

Inicialmente, no se puede determinar en qué bandeja del correo del usuario llegará la confirmación de la reserva. Por lo tanto, se recomienda revisar todas las bandejas (prioritarios, otros, correo no deseado) un tiempo después de realizar la reserva de manera exitosa.

También se aconseja recargar el correo durante unos minutos, ya que la confirmación puede no llegar de forma instantánea debido a posibles retrasos en el servidor de correo, problemas de red o la configuración del cliente de correo.
