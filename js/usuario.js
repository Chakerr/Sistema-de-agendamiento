document.addEventListener('DOMContentLoaded', () => {
    const cancelarReservaBtn = document.getElementById('cancelarReservaBtn');
    const codigoReservaInput = document.getElementById('codigoReserva');
    const resultadoReservasDiv = document.getElementById('resultadoReservas');

    // Función para cancelar una reserva
    cancelarReservaBtn.addEventListener('click', async () => {
        const codigoReserva = codigoReservaInput.value.trim();
        
        if (!codigoReserva) {
            resultadoReservasDiv.textContent = 'Por favor, ingresa el código de la reserva.';
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/ResEst/borrarRes/${codigoReserva}`, {
                method: 'DELETE',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ codigo: codigoReserva })
            });

            if (response.ok) {
                resultadoReservasDiv.textContent = 'Reserva cancelada exitosamente.';
                codigoReservaInput.value = ''; // Limpiar el campo de entrada
            } else {
                resultadoReservasDiv.textContent = 'Error al cancelar la reserva. Verifica el código.';
            }
        } catch (error) {
            console.error('Error de conexión:', error);
            resultadoReservasDiv.textContent = 'Error de conexión al cancelar la reserva. Intenta de nuevo.';
        }
    });
});
