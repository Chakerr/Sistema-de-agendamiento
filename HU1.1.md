# Versión 1.0
**31/08/2024**

## Historia de Usuario 1.1 – Registro e inicio de sesión por parte del usuario.

### Descripción:

Se requiere registrar e ingresar al usuario hacia la plataforma para poder efectuar procesos posteriores, por ende, el mismo usuario debe contar con credenciales establecidas y válidas.

**Como**: USUARIO (ESTUDIANTE).

**Quiero**: Acceder a la plataforma a través de un usuario y contraseña ya establecidos. En caso de aún no contar con credenciales válidas que existan en la base de datos, debe permitírsele al usuario llegar a la creación de las mismas para que pueda efectuar el login.

**Para**: Tener acceso al servicio de la plataforma, la cual le permitirá reservar un laboratorio para efectuar una correspondiente práctica.

### Criterio de aceptación 1 – Inscripción a la plataforma

**Dado**: Que realizar la verificación frente a estos dominios se va a asegurar que el interesado sea miembro adjunto de la Universidad Piloto de Colombia, ya sea como estudiante o como trabajador.

**Cuando**: El interesado esté llevando a cabo el proceso de inscripción.

**Entonces**: Se llevará a cabo la creación de las credenciales en la base de datos, donde la identificación del interesado será un usuario que corresponderá al correo otorgado y una contraseña generada aleatoriamente que le permitirá al ya creado usuario acceder a la plataforma. Adicionalmente, deberá llenar los campos de nombre, carrera, semestre, número de identidad (haciendo referencia a la cédula) y número de contacto, los cuales serán enviados a la base de datos al darle clic al botón "Register".

**Criterio de aceptación 1.1**: En el proceso de inscripción a la plataforma, el correo del potencial usuario (interesado) debe contener el dominio “@upc.edu.co” o “@unipiloto.edu.co”.

### Criterio de aceptación 2 – Inicio de sesión en la plataforma

**Dado**: Que al haber efectuado ya el proceso de inscripción, el usuario se encuentra con los insumos necesarios para realizar el Sign In.

**Cuando**: El usuario quiera iniciar sesión en la plataforma y dé clic al botón "Sign in".

**Entonces**: Se llevará a cabo la verificación de la existencia de las credenciales ingresadas en los campos “Email” y “Password”, datos que serán enviados a comparar a la base de datos y en caso de coincidir, se le permitirá el acceso a la plataforma; de caso contrario, se le negará la entrada a la plataforma acompañado del mensaje “Usuario o contraseña incorrectas”.

### Observación:
Es de importancia tener en cuenta que los laboratoristas cuentan todos con una única cuenta por la cual accederán a la plataforma. Por ende, cuando estén realizando el proceso de inscripción, los campos de carrera, semestre, número de identificación y número de contacto serán nulos, solo contarán con el correo y la contraseña que será suministrada por el sistema.
