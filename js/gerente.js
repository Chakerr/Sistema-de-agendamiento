document.addEventListener('DOMContentLoaded', () => {

    const fechaInput = document.getElementById('fecha');

    fechaInput.addEventListener('change', (event) => {
        const fechaSeleccionada = new Date(event.target.value);
        const diaSemana = fechaSeleccionada.getDay();

        if (diaSemana === 6) { // 0 es domingo
            alert('No se pueden seleccionar domingos. Por favor, elija otro día.');
            fechaInput.value = '';
            fechaInput.focus();
        }
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

    function consultarReservasPorFecha() {
        const fechaSeleccionada = document.getElementById('fecha').value;
    
        if (!fechaSeleccionada) {
            alert('Por favor, selecciona una fecha.');
            return;
        }
    
        fetch(``)
            .then(response => response.json())
            .then(reservas => {
                const resultadoReservasPorFecha = document.getElementById('resultadoReservasPorFecha');
                resultadoReservasPorFecha.innerHTML = '';
    
                if (Array.isArray(reservas) && reservas.length > 0) {
                    reservas.forEach(reserva => {
                        const reservaDiv = document.createElement('div');
                        reservaDiv.classList.add('reserva-item');
                        reservaDiv.innerHTML = `
                            <p><strong>ID:</strong> ${reserva.id}</p>
                            <p><strong>Fecha:</strong> ${reserva.fecha}</p>
                            <p><strong>Hora de Inicio:</strong> ${reserva.hora_inicio}</p>
                            <p><strong>Hora de Fin:</strong> ${reserva.hora_fin}</p>
                            <p><strong>Número de Personas:</strong> ${reserva.numero_personas}</p>
                        `;
                        resultadoReservasPorFecha.appendChild(reservaDiv);
                    });
                } else {
                    resultadoReservasPorFecha.textContent = `No hay reservas para la fecha seleccionada: ${fechaSeleccionada}.`;
                }
            })
            .catch(error => console.error('Error al consultar reservas por fecha:', error));
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
    document.getElementById('consultarReservasFechasBtn').addEventListener('click', consultarReservasPorFecha);
    
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

    const jsonData = {
        "categorias": ["Categoría A", "Categoría B", "Categoría C", "Categoría D"],
        "valores": [10, 20, 30, 40]
    };
    
    // Función para crear la gráfica de pastel
    function renderPieChart(data) {
        const ctx = document.getElementById('pieChart').getContext('2d');
    
        new Chart(ctx, {
            type: 'pie',
            data: {
                labels: data.categorias,
                datasets: [{
                    data: data.valores,
                    backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0'],
                    hoverBackgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0']
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'top'
                    },
                    tooltip: {
                        enabled: true
                    }
                }
            }
        });
    }

    function renderPieChart1(data) {
        const ctx = document.getElementById('pieChart1').getContext('2d');
    
        new Chart(ctx, {
            type: 'pie',
            data: {
                labels: data.categorias,
                datasets: [{
                    data: data.valores,
                    backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0'],
                    hoverBackgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0']
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'top'
                    },
                    tooltip: {
                        enabled: true
                    }
                }
            }
        });
    }
    
    // Llamar a la función para renderizar la gráfica usando los datos del JSON
    renderPieChart(jsonData);
    renderPieChart1(jsonData);

    function fetchEvents() {
        const dateInput = document.getElementById("date-input").value;
      
        if (!dateInput) {
          alert("Por favor selecciona una fecha.");
          return;
        }
      
        const eventContainer = document.getElementById("event-container");
        eventContainer.innerHTML = "Cargando eventos...";
      
        // Datos simulados de eventos
        const eventsData = {
          "2024-11-04": [
            {
              "title": "Reunión de equipo",
              "time": "10:00 AM",
              "description": "Discusión sobre el proyecto."
            },
            {
              "title": "Llamada con cliente",
              "time": "2:00 PM",
              "description": "Presentación de avances."
            }
          ],
          "2024-11-05": [
            {
              "title": "Entrenamiento",
              "time": "9:00 AM",
              "description": "Capacitación en nuevas tecnologías."
            }
          ]
        };
      
        // Obtener los eventos para la fecha seleccionada
        const events = eventsData[dateInput];
      
        // Limpiar el contenedor de eventos
        eventContainer.innerHTML = "";
      
        if (events && events.length > 0) {
          // Mostrar los eventos
          events.forEach(event => {
            const eventDiv = document.createElement("div");
            eventDiv.className = "event";
            eventDiv.innerHTML = '<strong>${event.title}</strong><br>${event.time}<br>${event.description}';
            eventContainer.appendChild(eventDiv);
          });
        } else {
          eventContainer.innerHTML = "No hay eventos para esta fecha.";
        }
      }
    
});
