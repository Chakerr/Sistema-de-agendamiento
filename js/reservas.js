document.addEventListener('DOMContentLoaded', async () => {
    const reservaForm = document.getElementById('reservaForm');
    const cantidadAcompanantesInput = document.getElementById('numero_personas');
    const acompanantesContainer = document.getElementById('acompanantesContainer');

    function obtenerInventario() {
        fetch('inventario.json')
            .then(response => response.json())
            .then(data => {
                const resultContainer = document.getElementById('inventarioContainer');
                resultContainer.innerHTML = '';

                if (data.length === 0) {
                    resultContainer.innerHTML = '<p>No hay inventario registrado.</p>';
                    return;
                }

                const table = document.createElement('table');
                const headerRow = document.createElement('tr');
                headerRow.innerHTML = '<th>ID</th><th>Equipo</th><th>Disponible</th><th>Seleccionar cantidad</th>';
                table.appendChild(headerRow);

                data.forEach((inventario) => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td class="id-cuadro">${inventario.idInventario}</td>
                        <td class="nombre-equipo">${inventario.equipo}</td>
                        <td>${inventario.cantidad}</td>
                        <td>
                                <input type="number" min="0" max="${inventario.cantidad}" id="cantidad_${inventario.idInventario}"
                                data-id="${inventario.idInventario}" data-nombre="${inventario.equipo}" data-inventario="true"
                                onchange="verificarCantidad(this, ${inventario.cantidad})" />
                        </td>
                    `;
                    table.appendChild(row);
                });

                resultContainer.appendChild(table);
            })
            .catch(error => console.error('Error:', error));
    }

    // Función para verificar si la cantidad seleccionada es válida
    function verificarCantidad(input, cantidadMaxima) {
        if (parseInt(input.value) > cantidadMaxima) {
            alert(`La cantidad seleccionada no puede exceder ${cantidadMaxima}`);
            input.value = cantidadMaxima;
        }
    }

    // Función para obtener el inventario seleccionado con cantidad mayor a 0
    function obtenerInventarioSeleccionado() {
        const inventarioSeleccionado = [];
        const cantidadInputs = document.querySelectorAll('input[type="number"][data-inventario="true"]'); // Solo selecciona los inputs con data-inventario="true"

        cantidadInputs.forEach(input => {
            const cantidad_seleccionada = parseInt(input.value); // Renombramos aquí
            if (cantidad_seleccionada > 0) {
                inventarioSeleccionado.push({
                    id_equipo: input.getAttribute('data-id'), // ID del equipo
                    nombre: input.getAttribute('data-nombre'), // Nombre del equipo
                    cantidad_seleccionada: cantidad_seleccionada // Renombramos aquí en el objeto
                });
            }
        });

        console.log('Inventario seleccionado:', inventarioSeleccionado);
        return inventarioSeleccionado;
    }


    // Establecer la fecha mínima a hoy
    const fechaInput = document.getElementById('fecha');
    const hoy = new Date();
    const formatoFecha = hoy.toISOString().split('T')[0]; // Formato YYYY-MM-DD
    fechaInput.setAttribute('min', formatoFecha);

    // Deshabilitar domingos
    fechaInput.addEventListener('change', (event) => {
        const fechaSeleccionada = new Date(event.target.value);
        const diaSemana = fechaSeleccionada.getDay();

        if (diaSemana === 6) { // 0 es domingo
            alert('No se pueden seleccionar domingos. Por favor, elija otro día.');
            fechaInput.value = '';
            fechaInput.focus();
        }
    });

    obtenerInventario();

    cantidadAcompanantesInput.addEventListener('input', () => {
        acompanantesContainer.innerHTML = '';

        const cantidadacom = parseInt(cantidadAcompanantesInput.value);
        if (isNaN(cantidadacom) || cantidadacom < 0) {
            return;
        }

        for (let i = 1; i <= cantidadacom; i++) {
            const div = document.createElement('div');
            div.classList.add('acompanante');

            div.innerHTML = `
                <h3>Estudiante ${i}</h3>
                <label for="nombre_acompanante_${i}">Nombre:</label>
                <input type="text" id="nombre_acompanante_${i}" name="nombre_acompanante_${i}" required>

                <label for="id_codigo_${i}">Código o Cédula (para no estudiante):</label>
                <input type="text" id="id_codigo_${i}" name="id_codigo_${i}" required>

                <label for="carrera_acompanante_${i}">Seleccione una Carrera:</label>
                <select id="carrera_acompanante_${i}" name="carrera_acompanante_${i}" required>
                <option value="" disabled selected> Seleccione una carrera</option>
                <option value="1">Ingeniería de Sistemas</option>
                <option value="2">Ingeniería de Telecomunicaciones</option>
                <option value="3">Ingeniería Mecatrónica</option>
                <option value="4">Ingeniería Financiera</option>
            `;
            acompanantesContainer.appendChild(div);

            const nombreField = div.querySelector(`#nombre_acompanante_${i}`);
            nombreField.addEventListener('input', (e) => {
                // Reemplaza cualquier carácter que no sea letra, espacio
                nombreField.value = nombreField.value.replace(/[^a-zA-Z\s]/g, '');
            });

        }
    });

    reservaForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const formData = new FormData(reservaForm);
        const acompanantes = [];
        const cantidadacom = parseInt(formData.get('numero_personas'));

        for (let i = 1; i <= cantidadacom; i++) {
            const acompanante = {
                id_codigo: formData.get(`id_codigo_${i}`),
                nombre: formData.get(`nombre_acompanante_${i}`),
                cedula: formData.get(`cedula_acompanante_${i}`)
            };
            acompanantes.push(acompanante);
        }

        const inventarioSeleccionado = [];
        document.querySelectorAll('input[type="number"][data-inventario="true"]').forEach(input => {
            const cantidadsele = parseInt(input.value);
            if (cantidadsele > 0) {
                inventarioSeleccionado.push({
                    id_equipo: input.getAttribute('data-id'),
                    nombre: input.getAttribute('data-nombre'),
                    cantidadsele: cantidadsele
                });
            }
        });

        const fecha = formData.get('fecha');
        const formattedDate = fecha ? fecha.split('/').reverse().join('-') : new Date().toISOString().split('T')[0];

        const horaInicio = formData.get('hora_inicio');
        const horasSumar = parseInt(formData.get('Horas'), 10);
        const horasFin = parseInt(formData.get('hora_inicio'), 10) + parseInt(formData.get('Horas'), 10);

        const data = {
            fecha: formattedDate,
            hora_inicio: horaInicio + ":00:00",
            horas: horasSumar,
            hora_fin: horasFin + ":00:00",
            numero_personas: parseInt(formData.get('numero_personas'), 10),
            estado: false,
            id_areaEstudio: {
                idArea: parseInt(formData.get('areaEstudio'), 10) // Asegúrate de que esto sea un número
            },
            equiposList: inventarioSeleccionado,
            estudiantesList: acompanantes
        };

        alert(JSON.stringify(data, null, 0));

        try {
            const response = await fetch('http://localhost:8080/ResEst/SaveRes', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data),
            });

            if (response.ok) {
                alert('Reserva creada con éxito');
                reservaForm.reset();
                acompanantesContainer.innerHTML = '';
            } else {
                alert('Error al crear la reserva');
            }
        } catch (error) {
            console.error('Error al crear la reserva:', error);
        }
    });

});