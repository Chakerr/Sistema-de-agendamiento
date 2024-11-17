const link = "http://localhost:8080"
document.addEventListener('DOMContentLoaded', () => {
    const cancelarReservaBtn = document.getElementById('cancelarReservaBtn');
    const codigoReservaInput = document.getElementById('codigoReserva');
    const messageDiv = document.getElementById('message');
    let codigoReserva; // Variable para almacenar el código de reserva

    // Función para enviar el código de reserva y mostrar el campo de token
    cancelarReservaBtn.addEventListener('click', async () => {
        codigoReserva = codigoReservaInput.value.trim();
        const storedId = sessionStorage.getItem('id');
        if (!codigoReserva) {
            messageDiv.textContent = 'Por favor, ingresa el código de la reserva.';
            return;
        }

        try {
            const response = await fetch(`${link}/ResEst/verificar?reservaId=${codigoReserva}&codigoEstudiante=${storedId}`);
            if (response.ok) {
                const deleteResponse = await fetch(`${link}/ResEst/borrarRes/${codigoReserva}`, {
                    method: 'DELETE',
                    headers: { 'Content-Type': 'application/json' }
                });

                alert("Se ha cancelado exitosamente su reserva, revise su correo con la confirmación");
            } else {
                alert("Error al cancelar su reserva, revise que su id está bien escrito");
            }
        } catch (error) {
            console.error('Error de conexión:', error);
            messageDiv.textContent = 'Error de conexión al enviar la confirmación. Intenta de nuevo.';
        }
    });
});