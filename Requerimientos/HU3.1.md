# Versión 1.1

**Fecha:** 23/10/2024

## Historia de Usuario 3.1 – Manejo de la plataforma por parte del administrador

### Descripción

El laboratorista, quien será el usuario administrador, podrá consultar las reservas hechas en la plataforma para estar al tanto de las prácticas que se van a realizar y los instrumentos que se utilizarán.

**Como:** USUARIO ADMINISTRADOR (LABORATORISTA)  
**Quiero:** Ver las reservas de un día específico a través de la interfaz, o bien, ver todas las reservas efectuadas en la plataforma.  
**Para:** Estar informado sobre qué prácticas se desarrollarán en los laboratorios, cuándo serán realizadas, y qué instrumentos del inventario se utilizarán. Adicionalmente, podrá revisar el estado de la sala y tener en cuenta los elementos que se utilizarán.

---

### Criterio de aceptación 1

El administrador debe iniciar sesión con sus credenciales.

**Dado que:** Los administradores (laboratoristas) tienen credenciales diferentes a las de los estudiantes, usando el dominio “@unipiloto.edu.co”, mientras que los estudiantes usan “@upc.edu.co”. Por ende, tendrán una interfaz diferente a la de los estudiantes.  
**Cuando:** El administrador haya ingresado sus credenciales y haya hecho clic en el botón “Sign in”.  
**Entonces:** Se realizará el inicio de sesión exitosamente y accederá a la interfaz administrativa para ver las reservas.

---

### Criterio de aceptación 2

El administrador puede consultar las reservas de un día específico.

**Dado que:** El administrador quiere conocer la información de las reservas hechas en un día en concreto.  
**Cuando:** El administrador haya seleccionado un día específico en el sistema.  
**Entonces:** La plataforma mostrará los resultados desde la base de datos de ese día. Si existen registros, se devolverán detalles como la hora, la práctica, el laboratorio y los elementos de inventario. Si no hay registros, se le informará al administrador que no hay reservas para ese día.

---

### Criterio de aceptación 3

El administrador puede consultar todas las reservas hasta la fecha.

**Dado que:** El administrador quiere conocer toda la información acerca de las reservas hechas hasta la fecha.  
**Cuando:** El administrador solicite ver todas las reservas en la interfaz.  
**Entonces:** La plataforma enviará los resultados de la base de datos con todas las reservas a la fecha. Se devolverán datos como el día, la hora, la práctica, el laboratorio y los elementos de inventario. Si no hay registros, se le informará que no se han efectuado reservas.

---

### Observación

Los laboratoristas tienen una única cuenta de correo con la que acceden a la plataforma. Durante el registro, al identificar el dominio “@unipiloto.edu.co”, el sistema sabrá que es un administrador, y el nombre corresponderá al que ellos decidan. Los campos de semestre, número de identidad y teléfono se dejarán vacíos, ya que los laboratoristas cuentan con una extensión telefónica interna para ser contactados, tal como sucede con otras entidades administrativas de la universidad.

El laboratorista tendrá acceso a los registros de la base de datos, pero no podrá manipularla. Este acceso es únicamente para supervisión y control de los laboratorios y los insumos, con el fin de generar informes posteriores.
