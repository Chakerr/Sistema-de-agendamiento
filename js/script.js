document.addEventListener('DOMContentLoaded', () => {
    const hamburger = document.querySelector('.hamburger');
    const nav = document.querySelector('nav');

    hamburger.addEventListener('click', () => {
        nav.classList.toggle('active');
    });

    const form = document.querySelector('form');
    const messageDiv = document.getElementById('message');

    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const formData = new FormData(form);
        let parseo = parseInt(formData.get('id_codigo'), 10);
        const data = {
            id_codigo : parseo,
            contrasena : formData.get('contrasena')
        }

        console.log(data);
        try {
            const response = await fetch('http://localhost:8080/login', {  // URL del backend
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data),
            });
            if (response.ok) {
                window.location.href = 'reservas.html';
            } else {
                messageDiv.textContent = 'Error en el inicio de sesión';
            }
        } catch (error) {
            console.log(error)
            messageDiv.textContent = 'Error de conexión. Intenta de nuevo.';
        }
    });
});
