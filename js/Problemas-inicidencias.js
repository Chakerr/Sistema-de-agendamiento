document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('incidenciaForm');
    const messageDiv = document.getElementById('message');

    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const formData = new FormData(form);
        try {
            const response = await fetch('http://localhost:8080/reportar-incidencia', {
                method: 'POST',
                body: formData
            });

            if (response.ok) {
                messageDiv.textContent = 'Incidencia reportada con éxito.';
                form.reset();
            } else {
                messageDiv.textContent = 'Error al reportar la incidencia.';
            }
        } catch (error) {
            messageDiv.textContent = 'Error de conexión.';
        }
    });
});
