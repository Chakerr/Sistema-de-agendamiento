document.addEventListener('DOMContentLoaded', () => {
    const emailStep = document.getElementById('emailStep');
    const tokenStep = document.getElementById('tokenStep');
    const passwordStep = document.getElementById('passwordStep');
    const sendTokenBtn = document.getElementById('sendTokenBtn');
    const verifyTokenBtn = document.getElementById('verifyTokenBtn');
    const changePasswordBtn = document.getElementById('changePasswordBtn');
    const messageDiv = document.getElementById('message');
    const baseUrl = "https://sistema-agendamiento-1-back-472b7073b8ab.herokuapp.com"; // Cambia esto según tu API

    let email = "";

    // Paso 1: Enviar token al correo
    sendTokenBtn.addEventListener('click', async () => {
        email = document.getElementById('email').value;

        if (!email) {
            messageDiv.textContent = "Por favor, ingresa un correo válido.";
            return;
        }

        try {
            const response = await fetch(`${baseUrl}/estudiantes/correo-existente/${email}`);
            if (response.ok) {
                messageDiv.textContent = "Se ha enviado un token a tu correo.";
                emailStep.style.display = 'none';
                tokenStep.style.display = 'block';
            } else {
                messageDiv.textContent = "Correo no encontrado en el sistema.";
            }
        } catch (error) {
            console.error(error);
            messageDiv.textContent = "Error al conectarse al servidor.";
        }
    });

    // Paso 2: Verificar token
    verifyTokenBtn.addEventListener('click', async () => {
        const token = document.getElementById('token').value;

        if (!token) {
            messageDiv.textContent = "Por favor, ingresa el token.";
            return;
        }

        try {
            const response = await fetch(`${baseUrl}/estudiantes/verificar-token/${token}`);
            if (response.ok) {
                messageDiv.textContent = "Token verificado correctamente.";
                tokenStep.style.display = 'none';
                passwordStep.style.display = 'block';
            } else {
                messageDiv.textContent = "Token inválido o expirado.";
            }
        } catch (error) {
            console.error(error);
            messageDiv.textContent = "Error al conectarse al servidor.";
        }
    });

    // Paso 3: Cambiar contraseña
    changePasswordBtn.addEventListener('click', async () => {
        const newPassword = document.getElementById('newPassword').value;
        const confirmPassword = document.getElementById('confirmPassword').value;

        if (!newPassword || !confirmPassword) {
            messageDiv.textContent = "Por favor, completa ambos campos.";
            return;
        }

        if (newPassword !== confirmPassword) {
            messageDiv.textContent = "Las contraseñas no coinciden.";
            return;
        }

        try {
            const response = await fetch(
                `${baseUrl}/estudiantes/cambio-contrasena/${email}/${newPassword}`,
                { method: 'PUT' }
            );

            if (response.ok) {
                messageDiv.textContent = "Contraseña actualizada exitosamente.";
                emailStep.style.display = 'block';
                tokenStep.style.display = 'none';
                passwordStep.style.display = 'none';
            } else {
                messageDiv.textContent = "Error al actualizar la contraseña.";
            }
        } catch (error) {
            console.error(error);
            messageDiv.textContent = "Error al conectarse al servidor.";
        }
    });
});
