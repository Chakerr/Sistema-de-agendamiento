const link = "https://sistema-agendamiento-1-back-472b7073b8ab.herokuapp.com"
document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('form');
    const messageDiv = document.getElementById('message');
    const tokenField = document.getElementById('token'); // Campo de token de entrada
    const tokenFieldDiv = document.getElementById('tokenField'); // Div que contiene el campo de token
    const verifyTokenButton = document.getElementById('verifyToken'); // Botón para verificar el token
    let storedData = {}; // Variable para almacenar los datos del formulario
    let countdownInterval; // Intervalo para el cronómetro

    const nombreField = document.getElementById('nombre');
    nombreField.addEventListener('input', (e) => {
        // Reemplaza cualquier carácter que no sea letra, espacio o vocal sin tilde
        nombreField.value = nombreField.value.replace(/[^a-zA-Z\s]/g, '');
    });

    // Función para iniciar el cronómetro de 5 minutos
    function startTimer(duration) {
        let timer = duration;
        countdownInterval = setInterval(() => {
            const minutes = Math.floor(timer / 60);
            const seconds = timer % 60;
            messageDiv.textContent = `Tiempo restante: ${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;

            if (--timer < 0) {
                clearInterval(countdownInterval);
                messageDiv.textContent = "El tiempo para registrar el token ha expirado.";
                tokenFieldDiv.style.display = "none"; // Ocultar el campo de token
            }
        }, 1000);
    }

    // Manejar el envío del formulario de registro
    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        const formData = new FormData(form);
        const  boton = document.getElementById('miBoton');
        const recaptchaResponse = grecaptcha.getResponse();
        if (!recaptchaResponse) {
        alert('Por favor, completa el reCAPTCHA');
    }
else{
        //boton.disabled = true;
        //boton.show = false;
        storedData = { // Guardar datos para reutilizar en la verificación del token
            id_codigo: formData.get('codigo_estudiante'),
            correo: formData.get('correo'),
            cedula: formData.get('cedula'),
            contrasena: formData.get('contrasena')
        };

        try {
            const response = await fetch(`${link}/pre-register`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(storedData),
            });
            if (response.ok) {
                alert("Se envió el token a su correo electrónico, por favor espere unos segundos");
                // Mostrar el campo para ingresar el token y el botón de verificación
                tokenFieldDiv.style.display = 'block';
                messageDiv.textContent = 'Registro exitoso. Ingresa el token enviado a tu correo electrónico.';
                startTimer(5 * 60); // Iniciar cronómetro de 5 minutos
            }
            else {
                const errorBody = await response.text();
                messageDiv.textContent = `Error al crear registrar: ${errorBody}`;
            }
            
        } catch (error) {
            const errorBody = await response.text();
            messageDiv.textContent = `Error al crear registrar: ${errorBody}`;
        }
    }
    });

    // Manejar la verificación del token
    verifyTokenButton.addEventListener('click', async () => {
        const token = tokenField.value; // Obtener el valor del token
        const tokenData = {
            token: token,
            id_codigo: storedData.id_codigo, // Usar datos guardados
            correo: storedData.correo,
            contrasena: storedData.contrasena
        };

        try {
            const tokenResponse = await fetch(`${link}/verify-token`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(tokenData),
            });
            if (tokenResponse.ok) {
               // clearInterval(countdownInterval); // Detener el cronómetro
                //messageDiv.textContent = 'Token verificado exitosamente. Ahora puedes iniciar sesión.';
                //setTimeout(() => {
                  //  window.location.href = 'login.html';
                //}, 2000);
                const selectCarrera = document.getElementById('carrera');
                const registro = {
                    id_codigo: storedData.id_codigo,
                    nombre: document.getElementById('nombre').value,
                    cedula: document.getElementById('cedula').value,
                    correo: storedData.correo,
                    contrasena: storedData.contrasena,
                    id_carrera: {
                        id: selectCarrera.value,
                        carrera: selectCarrera.options[selectCarrera.selectedIndex].text
                    }
                };

                // Guardar los datos finales de registro en la base de datos
                const registrarEstudiante = await fetch(`${link}/estudiantes/save`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(registro),
                });
                if(registrarEstudiante.ok){
                    clearInterval(countdownInterval); // Detener el cronómetro
                messageDiv.textContent = 'Token verificado exitosamente. Ahora puedes iniciar sesión.';
                setTimeout(() => {
                    window.location.href = 'login.html';
                }, 2000);
                }
            } else {
                const errorBody = await tokenResponse.text();
                messageDiv.textContent = `Error al registrar: ${errorBody}`;
            }
        } catch (error) {
            console.log(error);
            messageDiv.textContent = 'Error de conexión al verificar el token. Intenta de nuevo.';
        }
    });
});