document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('form');
    const messageDiv = document.getElementById('message');
    const tokenField = document.getElementById('tokenField'); // Campo de token
    const verifyTokenButton = document.getElementById('verifyToken'); // Botón para verificar el token

    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const formData = new FormData(form);
        const data = {
            id_codigo: formData.get('codigo_estudiante'),
            fecha: formData.get('fecha') || new Date().toISOString().split('T')[0], 
            hora_inicio: formData.get('hora_inicio') || "", 
            horas: formData.get('horas') ? parseInt(formData.get('horas'), 10) : null, 
            numero_personas: formData.get('numero_personas') ? parseInt(formData.get('numero_personas'), 10) : null,
            estado: formData.get('estado') === 'true', 
            id_areaEstudio: {
                idArea: formData.get('id_area') ? parseInt(formData.get('id_area'), 10) : null, 
                area: formData.get('area') || "" 
            },
            equiposList: [] 
        };

        try {
            const response = await fetch('http://localhost:8080/registro', {  // URL del backend
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data),
            });

            if (response.ok) {
                // Mostrar el campo para ingresar el token
                tokenField.style.display = 'block'; 
                verifyTokenButton.style.display = 'block'; // Mostrar el botón para verificar el token
                messageDiv.textContent = 'Registro exitoso. Ingresa el token enviado a tu correo electrónico.';
            } else {
                messageDiv.textContent = 'Error en el registro. Intenta de nuevo.';
            }
        } catch (error) {
            console.log(error);
            messageDiv.textContent = 'Error de conexión. Intenta de nuevo.';
        }
    });

    // Manejar la verificación del token
    verifyTokenButton.addEventListener('click', async () => {
        const token = tokenField.value; // Obtener el valor del token
        const tokenData = {
            token: token,
            id_codigo: formData.get('codigo_estudiante'), // Reutilizar el id_codigo
            correo: formData.get('correo'),
            contrasena: formData.get('contrasena')
        };

        try {
            const tokenResponse = await fetch('http://localhost:8080/verificar-token', { // URL para verificar el token
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(tokenData),
            });

            if (tokenResponse.ok) {
                messageDiv.textContent = 'Token verificado exitosamente. Ahora puedes iniciar sesión.';
                // Aquí podrías redirigir al usuario a la página de inicio de sesión o realizar otras acciones
            } else {
                messageDiv.textContent = 'Token inválido. Intenta de nuevo.';
            }
        } catch (error) {
            console.log(error);
            messageDiv.textContent = 'Error de conexión al verificar el token. Intenta de nuevo.';
        }
    });
});
