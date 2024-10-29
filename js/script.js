document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('form');
    const messageDiv = document.getElementById('message');
    const studentLoginBtn = document.getElementById('studentLoginBtn');
    const adminLoginBtn = document.getElementById('adminLoginBtn');
    const loginForm = document.getElementById('loginForm');
    const usernameLabel = document.querySelector('label[for="username"]');
    const usernameInput = document.getElementById('username');

    studentLoginBtn.addEventListener('click', () => {
        loginForm.style.display = 'block';
        loginForm.dataset.userType = 'student';
        usernameLabel.textContent = 'Codigo:';
        usernameInput.setAttribute('type', 'text');
        usernameInput.setAttribute('name', 'id_codigo');
    });

    adminLoginBtn.addEventListener('click', () => {
        loginForm.style.display = 'block';
        loginForm.dataset.userType = 'admin';
        usernameLabel.textContent = 'Correo:';
        usernameInput.setAttribute('type', 'email');
        usernameInput.setAttribute('name', 'correo');
    });


    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const formData = new FormData(form);
        const isAdmin = loginForm.dataset.userType === 'admin';
        const data = isAdmin
            ? {
                correo: formData.get('correo'),
                contrasena: formData.get('contrasena')
            }
            : {
                id_codigo: parseInt(formData.get('id_codigo'), 10),
                contrasena: formData.get('contrasena')
            };

        try {
            const url = isAdmin 
                ? 'http://localhost:8080/administradores/loginLab' 
                : 'http://localhost:8080/estudiantes/login';

            const response = await fetch(url, {  
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data),
            });

            if (response.ok) {
                const redirectPage = isAdmin ? 'laboratorista.html' : 'usuario.html';
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