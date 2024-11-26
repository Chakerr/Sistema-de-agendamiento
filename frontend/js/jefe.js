const link = "https://sistema-agendamiento-1-back-472b7073b8ab.herokuapp.com"
document.getElementById('InvBtn').addEventListener('click', obtenerInventario);
let pieChartInstance = null;

function consultarEstudiantes() {
    fetch(`${link}/jsons/estudiantes`)
        .then(response => response.json())
        .then(data => {
            const resultContainer = document.getElementById('resultContainer');
            resultContainer.innerHTML = '';

            if (data.length === 0) {
                resultContainer.innerHTML = '<p>No hay estudiantes registrados.</p>';
                return;
            }

            const table = document.createElement('table');
            const headerRow = document.createElement('tr');
            headerRow.innerHTML = '<th>ID</th><th>Nombre</th><th>Cédula</th><th>Visitas</th><th>Correo</th><th>Código</th>';
            table.appendChild(headerRow);

            data.forEach(estudiante => {
                const row = document.createElement('tr');
                row.innerHTML = ` 
                    <td>${estudiante.id_codigo}</td>
                    <td>${estudiante.nombre}</td>
                    <td>${estudiante.cedula}</td>
                    <td>${estudiante.visitas}</td>
                    <td>${estudiante.correo}</td>
                    <td>${estudiante.codigoCarnet}</td>`
                    ;
                table.appendChild(row);
            });

            resultContainer.appendChild(table);
        })
        .catch(error => console.error('Error:', error));
}

function obtenerInventario() {
    fetch(`${link}/inventarios/obtener`)
        .then(response => response.json())
        .then(data => {
            const resultContainer = document.getElementById('inventarioContainer');
            resultContainer.innerHTML = '';

            if (data.length === 0) {
                resultContainer.innerHTML = '<p>No hay inventario registrado.</p>';
                return;
            }

            // Aquí cambias el sort si lo deseas para ordenar alfabéticamente por el equipo
            data.sort((a, b) => {
                const equipoA = a.equipo.toLowerCase();
                const equipoB = b.equipo.toLowerCase();
                if (equipoA < equipoB) return -1;
                if (equipoA > equipoB) return 1;
                return 0;
            });

            const table = document.createElement('table');
            const headerRow = document.createElement('tr');
            headerRow.innerHTML = '<th>ID</th><th>Equipo</th><th>Disponible</th>';
            table.appendChild(headerRow);

            data.forEach((inventario) => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td class="id-cuadro">${inventario.idInventario}</td>
                    <td class="nombre-equipo">${inventario.equipo}</td>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${inventario.cantidad}</td>
                `;
                table.appendChild(row);
            });
            resultContainer.appendChild(table);
        })
        .catch(error => console.error('Error:', error));
}
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

    function consultarTotalVisitas() {
        fetch(`${link}/administradores/total-visitas`)
            .then(response => response.json())
            .then(data => {
                document.getElementById('numero-visitas').textContent = `${data}`;
            })
            .catch(error => console.error('Error al consultar el total de visitas:', error));
    }

    function consultarTotalReservas() {
        fetch(`${link}/administradores/total-reservas`)
            .then(response => response.json())
            .then(data => {
                document.getElementById('numero-reservas').textContent = `${data}`;
            })
            .catch(error => console.error('Error al consultar el total de reservas:', error));
    }

    function consultarReservas() {
        const codigoEstudiante = document.getElementById('codigoEstudianteReservas').value;
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

        consultarTotalReservas();
    }

    function mostrarHorarioReservas(reservas) {
        const resultadoReservasPorFecha = document.getElementById('resultadoReservasPorFecha');
        resultadoReservasPorFecha.innerHTML = ''; // Limpiar el contenido previo

        // Crear un objeto para las horas del día de 6 AM a 8 PM
        const horario = {};
        for (let i = 6; i <= 20; i++) { // Solo de 6 AM (6) a 8 PM (20)
            horario[i] = []; // Cambiar a un array para múltiples reservas
        }

        // Procesar reservas y llenar el horario
        reservas.forEach(reserva => {
            const horaInicio = new Date(`${reserva.fecha}T${reserva.hora_inicio}`);
            const horaFin = new Date(`${reserva.fecha}T${reserva.hora_fin}`);

            // Asegurarse de que se procesen solo las horas dentro del rango
            const inicioHora = Math.max(horaInicio.getHours(), 6);
            const finHora = Math.min(horaFin.getHours(), 20);

            // Asignar la reserva al array de la hora de inicio
            horario[inicioHora].push({
                id: reserva.id,
                fecha: reserva.fecha,
                horas: reserva.horas,
                numero_personas: reserva.numero_personas,
                estado: reserva.estado,
                area: reserva.id_areaEstudio.area // Nombre del área de estudio
            });
        });

        // Mostrar las reservas en el horario
        for (let i = 6; i <= 20; i++) { // Solo de 6 AM (6) a 8 PM (20)
            const reservasPorHora = horario[i];
            const reservaDiv = document.createElement('div');
            reservaDiv.classList.add('reserva-item');

            // Formatear la hora a dos dígitos
            const formattedHour = String(i).padStart(2, '0');

            if (reservasPorHora.length > 0) {
                reservaDiv.innerHTML = `<p>${formattedHour}:00 - Reservas:</p>`;
                reservasPorHora.forEach(reserva => {
                    const estadoReserva = reserva.estado ? 'Asistido' : 'No asistido'; // Cambio aquí
                    reservaDiv.innerHTML += `
                        <p>
                            ID: ${reserva.id}, 
                            Horas: ${reserva.horas}, 
                            Número de Personas: ${reserva.numero_personas}, 
                            Estado: ${estadoReserva}, 
                            Área de Estudio: ${reserva.area}
                        </p>
                    `;
                });
            } else {
                reservaDiv.innerHTML = `<p>${formattedHour}:00 - No hay reservas</p>`;
            }

            resultadoReservasPorFecha.appendChild(reservaDiv);
        }
    }

    // Función para consultar reservas por fecha
    function consultarReservasPorFecha() {
        const fechaSeleccionada = document.getElementById('fecha').value;

        if (!fechaSeleccionada) {
            alert('Por favor, selecciona una fecha.');
            return;
        }

        fetch(`${link}/administradores/reservas-fecha/${fechaSeleccionada}`)
            .then(response => response.json())
            .then(reservas => {
                // Convertir horas al formato de dos dígitos
                reservas.forEach(reserva => {
                    reserva.hora_inicio = reserva.hora_inicio.padStart(8, '0');
                    reserva.hora_fin = reserva.hora_fin.padStart(8, '0');
                });

                const reservasFiltradas = reservas.filter(reserva => reserva.fecha === fechaSeleccionada);
                console.log('Reservas filtradas:', reservasFiltradas); // Verifica el resultado del filtrado
                mostrarHorarioReservas(reservasFiltradas);
            })
            .catch(error => console.error('Error al consultar reservas por fecha:', error));
    }

    // Asociar la función al botón
    document.getElementById('consultarReservasFechasBtn').addEventListener('click', consultarReservasPorFecha);

    function verDetallesReserva() {
        console.log("Botón 'Ver Detalles de Reservas' presionado."); // Comprobación inicial

        fetch(`${link}/administradores/reservas`)
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

                    // Crear lista de equipos
                    const equipos = reserva.equiposList.map(equipo => {
                        return `${equipo.nombre} (ID:    ${equipo.id_equipo}, Cantidad: ${equipo.cantidad})`;
                    }).join(', ');

                    reservaDetalle.innerHTML =
                        `<p><strong>ID:</strong> ${reserva.id}</p>
                    <p><strong>Fecha:</strong> ${reserva.fecha}</p>
                    <p><strong>Hora de Inicio:</strong> ${reserva.hora_inicio}</p>
                   <p><strong>Horas:</strong> ${reserva.horas}</p>
                    <p><strong>Hora de Fin:</strong> ${reserva.hora_fin}</p>
                   <p><strong>Número de Personas:</strong> ${reserva.numero_personas}</p>
                <p><strong>Estado:</strong> ${reserva.estado ? 'Activa' : 'Inactiva'}</p>
                    <p><strong>Área de Estudio:</strong> ${reserva.id_areaEstudio.area}</p>
                    <p><strong>Equipos:</strong> ${equipos || 'Ninguno'}</p>`
                        ;

                    detallesReserva.appendChild(reservaDetalle);
                });
            })
            .catch(error => console.error('Error al consultar detalles de la reserva:', error));
    }

    document.getElementById('consultarReservasBtn').addEventListener('click', consultarReservas);
    document.getElementById('ver-detalles').addEventListener('click', verDetallesReserva);
    document.getElementById('consultarReservasFechasBtn').addEventListener('click', consultarReservasPorFecha);

    document.getElementById('consultarVisitasBtn').addEventListener('click', function () {
        const codigoEstudiante = document.getElementById('codigoEstudianteVisitas').value;
        fetch(`${link}/administradores/visitas/${codigoEstudiante}`)
            .then(response => response.json())
            .then(data => {
                document.getElementById('resultadoVisitas').textContent = `Número de visitas del estudiante ${codigoEstudiante}: ${data}`;
            })
            .catch(error => console.error('Error al consultar visitas:', error));

        consultarTotalVisitas();
    });

    document.getElementById('consultarInventarioBtn').addEventListener('click', function () {
        // Obtener los valores de los inputs
        const codigoInventario = document.getElementById('codigoInventario').value;
        const codigoInventarioCantidad = document.getElementById('codigoInventarioCantidad').value;

        // Validar que ambos campos tengan datos
        if (!codigoInventario || !codigoInventarioCantidad) {
            alert('Por favor, ingresa ambos valores (Código inventario y cantidad).');
            return;
        }

        // Crear el objeto con los datos a enviar
        const datosInventario = {
            equipo: codigoInventario,
            cantidad: parseInt(codigoInventarioCantidad)
        };
        //alert(JSON.stringify(datosInventario));
        // Hacer la petición POST
        fetch(`${link}/JefeL/abastecer`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json' // Especificamos que estamos enviando JSON
            },
            body: JSON.stringify(datosInventario) // Convertimos el objeto a JSON
        })
            .then(response => {
                if (response.ok) {
                    document.getElementById('resultado').textContent = `Inventario agregado con éxito.`;
                    return response.json(); // Si la respuesta es OK, parseamos el JSON
                } else {
                    document.getElementById('resultado').textContent = `Error al agregar inventario.`;
                    throw new Error('Error en la respuesta del servidor');
                }
            })
        /*
        .then(response => {
            document.getElementById('resultado').textContent = `Inventario agregado con éxito. ${response.message}`;
        })
        .catch(error => {
            document.getElementById('resultado').textContent = `Error al agregar inventario: ${error.message}`;
        });
        */
    });

    consultarTotalVisitas();
    consultarTotalReservas();

    /**
     * 
     * Generación de gráfica para el inventario
     */

    // Función para generar colores dinámicos
    function generateColors(count) {
        const colors = [];
        for (let i = 0; i < count; i++) {
            const color = `hsl(${(i * 360 / count)}, 70%, 60%)`; // Genera un color HSL único para cada valor
            colors.push(color);
        }
        return colors;
    }

    // Función para procesar el JSON dinámico
    function processJsonData(json) {
        const categorias = Object.keys(json);
        const valores = Object.values(json);

        // Filtrar categorías y valores donde el valor es mayor a 0
        const categoriasConValores = categorias
            .map((categoria, index) => ({ categoria, valor: valores[index] }))
            .filter(item => item.valor > 0) // Eliminar entradas con valor 0
            .map(item => `${item.categoria}: ${item.valor}`); // Concatenar nombre y valor

        const valoresFiltrados = valores.filter(valor => valor > 0);

        return { categorias: categoriasConValores, valores: valoresFiltrados };
    }

    // Función para crear la gráfica de pastel
    function renderPieChart(data) {
        const canvas = document.getElementById('pieChart'); // Usa el mismo canvas
        const parent = canvas.parentNode;

        // Validar si no hay datos o si los valores incluyen 500 y las categorías incluyen "status"
        if (
            data.valores.length === 0 ||
            data.categorias.length === 0 ||
            (data.valores.includes(500) && data.categorias.some(categoria => categoria.toLowerCase() === "status: 500"))
        ) {
            // Buscar si ya existe un mensaje previo
            let message = document.getElementById('noDataMessage');
            if (!message) {
                // Crear el mensaje si no existe
                message = document.createElement('p');
                message.id = 'noDataMessage';
                message.textContent = "No hay información para esa fecha";
                message.style.color = "red"; // Estilo opcional
                message.style.textAlign = "center";
                parent.appendChild(message); // Insertar mensaje en el contenedor
            }

            // Ocultar el canvas, pero no eliminarlo
            canvas.style.display = 'none';

            // Limpiar la instancia del gráfico si existe
            if (pieChartInstance) {
                pieChartInstance.destroy();
                pieChartInstance = null;
            }

            return; // Salir de la función
        }

        // Si hay datos válidos, eliminar mensaje y mostrar el canvas
        const existingMessage = document.getElementById('noDataMessage');
        if (existingMessage) {
            existingMessage.remove(); // Eliminar el mensaje
        }
        canvas.style.display = 'block'; // Mostrar el canvas

        // Destruir el gráfico anterior si existe
        if (pieChartInstance) {
            pieChartInstance.destroy();
        }

        // Crear el nuevo gráfico y almacenarlo en la variable global
        pieChartInstance = new Chart(canvas.getContext('2d'), {
            type: 'bar', // Sigue siendo 'bar'
            data: {
                labels: data.categorias,
                datasets: [{
                    label: '', // No mostrar leyenda
                    data: data.valores,
                    backgroundColor: generateColors(data.categorias.length),
                    hoverBackgroundColor: generateColors(data.categorias.length)
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        display: false // Ocultar leyenda
                    },
                    tooltip: {
                        enabled: true
                    }
                },
                indexAxis: 'y', // Aquí está el cambio: eje invertido
                scales: {
                    x: {
                        beginAtZero: true,
                        title: {
                            display: true,
                            text: 'Valores' // Título del eje X
                        }
                    },
                    y: {
                        beginAtZero: true,
                        title: {
                            display: false // No mostrar título del eje Y
                        }
                    }
                },
                animation: {
                    onComplete: function () {
                        const ctx = canvas.getContext('2d');
                        const chart = this;
                        ctx.font = '12px Arial';
                        ctx.fillStyle = 'black';
                        chart.data.datasets[0].data.forEach((value, index) => {
                            const meta = chart.getDatasetMeta(0).data[index];
                            const x = meta.x;
                            const y = meta.y;
                            ctx.fillText(value, x + 5, y); // Mostrar valor al lado de cada barra
                        });
                    }
                }
            }
        });
    }



    async function fetchDataAndRenderChart(fecha) {
        try {
            const response = await fetch(`${link}/JefeL/stats/${fecha}`, {
                method: 'GET'
            });

            const jsonData = await response.json();

            const processedData = processJsonData(jsonData);
            renderPieChart(processedData);
        } catch (error) {
            console.error("Error al obtener los datos:", error);
        }
    }


    // Agregar el evento de clic al botón
    document.getElementById('loadChartBtn').addEventListener('click', () => {
        const fecha = document.getElementById('fechachart').value;
        if (fecha) {
            fetchDataAndRenderChart(fecha);
        } else {
            alert("Por favor, selecciona una fecha.");
        }
    });

});
document.addEventListener("DOMContentLoaded", function () {
    // Verificar si 'id' está almacenado en sessionStorage
    if (!sessionStorage.getItem('jefe')) {
        // Si no existe, redirigir a inicio.html
        alert('No tienes permiso para acceder a esta página.');
        window.location.href = 'index.html'; // Cambia a la ruta correspondiente de tu página de inicio
    }
});