document.addEventListener('DOMContentLoaded', () => {
    function leerRFID() {
        return 'RFID_' + Math.random().toString(36).substr(2, 9);
    }

    function enviarAlBackend(codigo) {
        fetch('https://tu-backend-api.com/api/confirmarAsistencia', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ codigoRFID: codigo })
        })
        .then(response => response.json())
        .then(data => {
            console.log('Confirmación enviada:', data);
            alert('Asistencia confirmada correctamente.');
        })
        .catch(error => {
            console.error('Error al enviar los datos:', error);
            alert('Hubo un error al confirmar la asistencia.');
        });
    }

    document.getElementById('confirmarAsistenciaBtn').addEventListener('click', function() {
        let codigo = leerRFID();
        enviarAlBackend(codigo);
    });

    function cargarReservas() {
        fetch('http://localhost:8080/administradores/reservas')
            .then(response => response.json())
            .then(reservas => {
                let listaReservas = document.getElementById('listaReservas');
                listaReservas.innerHTML = '';
                reservas.forEach(reserva => {
                    let reservaDiv = document.createElement('div');
                    reservaDiv.textContent = `ID: ${reserva.id} - Fecha: ${reserva.fecha} - Hora Inicio: ${reserva.hora_inicio} - Hora Fin: ${reserva.hora_fin} - Número de Personas: ${reserva.numero_personas}`;
                    listaReservas.appendChild(reservaDiv);
                });
            })
            .catch(error => console.error('Error al cargar reservas:', error));
    }

    function consultarTotalVisitas() {
        fetch('http://localhost:8080/administradores/total-visitas')
            .then(response => response.json())
            .then(data => {
                document.getElementById('numero-visitas').textContent = `${data}`;
            })
            .catch(error => console.error('Error al consultar el total de visitas:', error));
    }

    function consultarTotalReservas() {
        fetch('http://localhost:8080/administradores/total-reservas')
            .then(response => response.json())
            .then(data => {
                document.getElementById('numero-reservas').textContent = `${data}`;
            })
            .catch(error => console.error('Error al consultar el total de reservas:', error));
    }

    function consultarReservas() {
        const codigoEstudiante = document.getElementById('codigoEstudianteReservas').value;
        fetch(`http://localhost:8080/administradores/reservas-activas/${codigoEstudiante}`)
            .then(response => response.json())
            .then(reservas => {
                const resultadoReservas = document.getElementById('resultadoReservas');
                resultadoReservas.innerHTML = '';
    
                if (Array.isArray(reservas) && reservas.length > 0) {
                    reservas.forEach(reserva => {
                        const reservaDiv = document.createElement('div');
                        reservaDiv.classList.add('reserva-item');
                        reservaDiv.innerHTML = `
                            <p><strong>ID:</strong> ${reserva.id}</p>
                            <p><strong>Fecha:</strong> ${reserva.fecha}</p>
                            <p><strong>Hora de Inicio:</strong> ${reserva.hora_inicio}</p>
                            <p><strong>Horas:</strong> ${reserva.horas}</p>
                            <p><strong>Hora de Fin:</strong> ${reserva.hora_fin}</p>
                            <p><strong>Número de Personas:</strong> ${reserva.numero_personas}</p>
                            <p><strong>Estado:</strong> ${reserva.estado ? 'Activa' : 'Inactiva'}</p>
                            <p><strong>Área de Estudio:</strong> ${reserva.id_areaEstudio.area}</p>
                            <p><strong>Equipos:</strong> ${reserva.equiposList.length > 0 ? reserva.equiposList.join(', ') : 'Ninguno'}</p>
                        `;
                        resultadoReservas.appendChild(reservaDiv);
                    });
                } else {
                    resultadoReservas.textContent = `No hay reservas para el estudiante ${codigoEstudiante}.`;
                }
            })
            .catch(error => console.error('Error al consultar reservas:', error));
    
        consultarTotalReservas();
    }

    function verDetallesReserva() {
        console.log("Botón 'Ver Detalles de Reservas' presionado."); // Comprobación inicial
        
        fetch('http://localhost:8080/administradores/reservas')
            .then(response => {
                console.log("Respuesta recibida del servidor:", response);
                return response.json();
            })
            .then(reservas => {
                console.log("Datos de reservas:", reservas); // Verifica los datos obtenidos
                const detallesReserva = document.getElementById('detallesReserva');
                detallesReserva.style.display = 'block'; // Mostrar detalles
                detallesReserva.innerHTML = '';
    
                reservas.forEach(reserva => {
                    const reservaDetalle = document.createElement('div');
                    reservaDetalle.classList.add('reserva-detalle');
                    reservaDetalle.innerHTML = `
                        <p><strong>ID:</strong> ${reserva.id}</p>
                        <p><strong>Fecha:</strong> ${reserva.fecha}</p>
                        <p><strong>Hora de Inicio:</strong> ${reserva.hora_inicio}</p>
                        <p><strong>Horas:</strong> ${reserva.horas}</p>
                        <p><strong>Hora de Fin:</strong> ${reserva.hora_fin}</p>
                        <p><strong>Número de Personas:</strong> ${reserva.numero_personas}</p>
                        <p><strong>Estado:</strong> ${reserva.estado ? 'Activa' : 'Inactiva'}</p>
                        <p><strong>Área de Estudio:</strong> ${reserva.id_areaEstudio.area}</p>
                        <p><strong>Equipos:</strong> ${reserva.equiposList.length > 0 ? reserva.equiposList.join(', ') : 'Ninguno'}</p>
                    `;
                    detallesReserva.appendChild(reservaDetalle);
                });
            })
            .catch(error => console.error('Error al consultar detalles de la reserva:', error));
    }

    function cargarCalendario(reservas) {
        const eventos = reservas.map(reserva => ({
            title: `Reserva: ${reserva.id}`,
            start: `${reserva.fecha}T${reserva.hora_inicio}`, // Formato ISO
            end: `${reserva.fecha}T${reserva.hora_fin}` // Formato ISO
        }));

        $('#calendar').fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            defaultDate: moment().format('YYYY-MM-DD'),
            editable: false,
            events: eventos,
            eventLimit: true // permite ver más eventos si hay muchos
        });
    }

    document.getElementById('consultarReservasBtn').addEventListener('click', consultarReservas);
    document.getElementById('ver-detalles').addEventListener('click', verDetallesReserva);
    
    document.getElementById('consultarVisitasBtn').addEventListener('click', function() {
        const codigoEstudiante = document.getElementById('codigoEstudianteVisitas').value;
        fetch(`http://localhost:8080/administradores/visitas/${codigoEstudiante}`)
            .then(response => response.json())
            .then(data => {
                document.getElementById('resultadoVisitas').textContent = `Número de visitas del estudiante ${codigoEstudiante}: ${data}`;
            })
            .catch(error => console.error('Error al consultar visitas:', error));

        consultarTotalVisitas();
    });

    consultarTotalVisitas();
    consultarTotalReservas();
});
