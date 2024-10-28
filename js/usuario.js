document.addEventListener('DOMContentLoaded', async () => {
    const listaReservas = document.getElementById('listaReservas');

    try {
        const response = await fetch('http://localhost:8080/mis-reservas');  // URL del backend
        const reservas = await response.json();

        if (reservas.length === 0) {
            listaReservas.innerHTML = '<li>No tienes reservas activas.</li>';
        } else {
            reservas.forEach(reserva => {
                const li = document.createElement('li');
                li.textContent = `Reserva: ${reserva.nombreLaboratorio} - Fecha: ${reserva.fecha}`;
                listaReservas.appendChild(li);
            });
        }
    } catch (error) {
        listaReservas.innerHTML = '<li>Error al cargar las reservas.</li>';
    }
});
