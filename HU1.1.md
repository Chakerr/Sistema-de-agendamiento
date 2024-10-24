# Versión 1.1

**Fecha:** 23/10/2024

## Historia de Usuario 1.1 – Registro e inicio de sesión por parte del usuario

### Descripción:

Se requiere registrar e ingresar al usuario en la plataforma para poder efectuar procesos posteriores. Por ende, el mismo usuario debe contar con credenciales establecidas y válidas.

**Como:** USUARIO (ESTUDIANTE/LABORATORISTA).

**Quiero:** Acceder a la plataforma a través de un usuario y contraseña ya establecidos. En caso de aún no contar con credenciales válidas que existan en la base de datos, debe permitírsele al usuario crear las mismas para que pueda efectuar el login.

**Para:** Tener acceso al servicio de la plataforma, la cual le permitirá reservar una sala de laboratorio para efectuar una práctica correspondiente.

---

### Criterio de aceptación 1:

En el proceso de inscripción a la plataforma, el correo del potencial usuario (interesado) debe contener el dominio “@upc.edu.co” o “@unipiloto.edu.co”.

**Dado:** Que al realizar la verificación frente a estos dominios, se asegura que el interesado sea miembro de la Universidad Piloto de Colombia, ya sea como estudiante o trabajador.

**Cuando:** El interesado esté llevando a cabo el proceso de inscripción.

**Entonces:** Se llevará a cabo la creación de las credenciales, registrándolas en la base de datos de manera exitosa. La identificación del interesado será un usuario que corresponderá al correo otorgado y una contraseña generada aleatoriamente, que le permitirá al usuario acceder a la plataforma.

Adicionalmente, el usuario deberá llenar los campos de nombre, carrera, semestre, número de identidad (cédula) y número de contacto, los cuales serán enviados a la base de datos al hacer clic en el botón "Register".

---

### Criterio de aceptación 2:

El usuario debe ser capaz de iniciar sesión en la plataforma con credenciales ya existentes en la base de datos.

**Dado:** Que al haber efectuado ya el proceso de inscripción, el usuario cuenta con los insumos necesarios para realizar el Sign In.

**Cuando:** El usuario quiera iniciar sesión en la plataforma y haga clic en el botón “Sign in”.

**Entonces:** Se llevará a cabo la verificación de la existencia de las credenciales ingresadas en los campos “Email” y “Password”. Estos datos serán enviados para comparar con la base de datos, y en caso de coincidir, se le permitirá el acceso a la plataforma. De lo contrario, se le negará la entrada acompañado del mensaje “Usuario o contraseña incorrectas”.

---

### OBSERVACIÓN:

Es importante tener en cuenta que todos los laboratoristas cuentan con una única cuenta para acceder a la plataforma. Por lo tanto, cuando estén realizando el proceso de registro, los campos de carrera, semestre, número de identificación y número de contacto serán nulos, y solo se considerará el correo y la contraseña, los cuales serán suministrados por el sistema.
