const link = "https://sistema-agendamiento-1-back-472b7073b8ab.herokuapp.com"
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

document.addEventListener("DOMContentLoaded", function () {
    // Verificar si 'id' está almacenado en sessionStorage
    if (!sessionStorage.getItem('id')) {
        // Si no existe, redirigir a inicio.html
        alert('No tienes permiso para acceder a esta página.');
        window.location.href = 'index.html'; // Cambia a la ruta correspondiente de tu página de inicio
    }
});

document.getElementById('consultarReservasBtn').addEventListener('click', () => {
    // Obtener el valor de 'id' desde sessionStorage
    const codigoEstudiante = sessionStorage.getItem('id');

    if (!codigoEstudiante) {
        alert('No se encontró un código de estudiante en la sesión.');
        return;
    }

    // Realizar la consulta a la API
    fetch(`${link}/administradores/reservas-activas/${codigoEstudiante}`)
        .then(response => response.json())
        .then(reservas => {
            const resultadoReservas = document.getElementById('resultadoReservas');
            resultadoReservas.innerHTML = '';

            if (Array.isArray(reservas) && reservas.length > 0) {
                reservas.forEach(reserva => {
                    const reservaDiv = document.createElement('div');
                    reservaDiv.classList.add('reserva-item');

                    // Procesar equiposList para mostrar id_equipo, nombre y cantidad
                    const equiposInfo = reserva.equiposList.length > 0
                        ? reserva.equiposList.map(equipo =>
                            `ID: ${equipo.id_equipo}, Nombre: ${equipo.nombre}, Cantidad: ${equipo.cantidad}`
                          ).join('<br>')
                        : 'Ninguno';

                    reservaDiv.innerHTML = `
                    <p><strong>ID:</strong> ${reserva.id}</p>
                    <p><strong>Fecha:</strong> ${reserva.fecha}</p>
                    <p><strong>Hora de Inicio:</strong> ${reserva.hora_inicio}</p>
                    <p><strong>Horas:</strong> ${reserva.horas}</p>
                    <p><strong>Hora de Fin:</strong> ${reserva.hora_fin}</p>
                    <p><strong>Número de Personas:</strong> ${reserva.numero_personas}</p>
                    <p><strong>Estado:</strong> ${reserva.estado ? 'Inactiva' : 'Activa'}</p>
                    <p><strong>Área de Estudio:</strong> ${reserva.id_areaEstudio.area}</p>
                    <p><strong>Equipos:</strong><br> ${equiposInfo}</p>
                    `;
                    resultadoReservas.appendChild(reservaDiv);
                });
            } else {
                resultadoReservas.textContent = `No hay reservas para el estudiante ${codigoEstudiante}.`;
            }
        })
        .catch(error => console.error('Error al consultar reservas:', error));
});