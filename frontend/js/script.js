const link = "https://sistema-agendamiento-1-back-472b7073b8ab.herokuapp.com"
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
        showLoginForm('student', 'Código o Cedula (para no estudiante):', 'text', 'id_codigo');
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
            ? `${link}/administradores/loginLab`
            : userType === 'manager'
                ? `${link}/administradores/loginLab` // URL para Gerente
                : userType === 'chief'
                    ? `${link}/administradores/loginLab` // URL para Jefe
                    : userType === 'developer'
                        ? `${link}/jsons/loginAdm` // URL para Desarrollador
                        : `${link}/estudiantes/login`; // URL para Estudiante

        try {
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data),
            });

            if (response.ok) {
                if (userType === 'student') {
                    sessionStorage.setItem('id', data.id_codigo);
                    sessionStorage.removeItem('jefe'); // Elimina el valor de 'jefe' si existe
                } else {
                    sessionStorage.setItem('jefe', data.id_codigo);
                    sessionStorage.removeItem('id'); // Elimina el valor de 'id' si existe
                }
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