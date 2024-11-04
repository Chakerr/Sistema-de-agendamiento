document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('form');
    const messageDiv = document.getElementById('message');
    const studentLoginBtn = document.getElementById('studentLoginBtn');
    const adminLoginBtn = document.getElementById('adminLoginBtn');
    const managerLoginBtn = document.getElementById('managerLoginBtn');
    const chiefLoginBtn = document.getElementById('chiefLoginBtn');
    const developerLoginBtn = document.getElementById('developerLoginBtn');
    const loginForm = document.getElementById('loginForm');
    const usernameLabel = document.querySelector('label[for="username"]');
    const usernameInput = document.getElementById('username');

    const showLoginForm = (userType, label, inputType, inputName) => {
        loginForm.style.display = 'block';
        loginForm.dataset.userType = userType;
        usernameLabel.textContent = label;
        usernameInput.setAttribute('type', inputType);
        usernameInput.setAttribute('name', inputName);
    };

    studentLoginBtn.addEventListener('click', () => {
        showLoginForm('student', 'Código:', 'text', 'id_codigo');
    });

    adminLoginBtn.addEventListener('click', () => {
        showLoginForm('admin', 'Correo:', 'email', 'correo');
    });

    managerLoginBtn.addEventListener('click', () => {
        showLoginForm('manager', 'Correo:', 'email', 'correo');
    });

    chiefLoginBtn.addEventListener('click', () => {
        showLoginForm('chief', 'Correo:', 'email', 'correo');
    });

    developerLoginBtn.addEventListener('click', () => {
        showLoginForm('developer', 'Correo:', 'email', 'correo');
    });

    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const formData = new FormData(form);
        const userType = loginForm.dataset.userType;

        const data = {
            contrasena: formData.get('contrasena')
        };


        if (userType !== 'student') {
            data.correo = formData.get('correo');
        } else {
            data.id_codigo = parseInt(formData.get('id_codigo'), 10);
        }


        const url = userType === 'admin'
            ? 'http://localhost:8080/administradores/loginLab'
            : userType === 'manager'
                ? 'http://localhost:8080/' // URL para Gerente
                : userType === 'chief'
                    ? 'http://localhost:8080/' // URL para Jefe
                    : userType === 'developer'
                        ? 'http://localhost:8080/' // URL para Desarrollador
                        : 'http://localhost:8080/estudiantes/login'; // URL para Estudiante

        try {
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data),
            });

            if (response.ok) {
                const redirectPage = userType === 'admin'
                    ? 'laboratorista.html'
                    : userType === 'manager'
                        ? 'gerente.html'
                        : userType === 'chief'
                            ? 'jefe.html'
                            : userType === 'developer'
                                ? 'desarrollador.html'
                                : 'usuario.html';
                window.location.href = redirectPage;
            } else {
                messageDiv.textContent = 'Error en el inicio de sesión';
            }
        } catch (error) {
            console.log(error);
            messageDiv.textContent = 'Error de conexión. Intenta de nuevo.';
        }
    });
});