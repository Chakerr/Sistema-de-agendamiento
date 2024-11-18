document.addEventListener('DOMContentLoaded', () => {
    const reservas = [
        {
            id: 1,
            laboratorio: 'Laboratorio 1',
            fecha: '2024-09-30',
            hora: '10:00',
            materiales: ['Proyector', 'Computadoras', 'Sillas']
        },
        {
            id: 2,
            laboratorio: 'Laboratorio 2',
            fecha: '2024-10-01',
            hora: '14:00',
            materiales: ['Microscopios', 'Pizarras', 'Bancos']
        },
        // Agrega más reservas
    ];

    const reservasList = document.getElementById('reservasList');


    function mostrarReservas() {
        reservasList.innerHTML = '';
        reservas.forEach(reserva => {
            const reservaDiv = document.createElement('div');
            reservaDiv.className = 'reserva';
            reservaDiv.innerHTML = `
                <strong>${reserva.laboratorio}</strong>
                <p>${reserva.fecha} a las ${reserva.hora}</p>
            `;
            reservaDiv.addEventListener('click', () => mostrarDetallesReserva(reserva));
            reservasList.appendChild(reservaDiv);
        });
    }

    function mostrarDetallesReserva(reserva) {
        document.getElementById('reservaTitulo').innerText = `Detalles de la Reserva ${reserva.id}`;
        document.getElementById('reservaLaboratorio').innerText = reserva.laboratorio;
        document.getElementById('reservaFecha').innerText = reserva.fecha;
        document.getElementById('reservaHora').innerText = reserva.hora;

        const materialesList = document.getElementById('materialesList');
        materialesList.innerHTML = '';
        reserva.materiales.forEach(material => {
            const li = document.createElement('li');
            li.innerText = material;
            materialesList.appendChild(li);
        });

        document.getElementById('reservaDetalles').style.display = 'block';
    }

    mostrarReservas();
});
