document.addEventListener('DOMContentLoaded', async () => {
    const reservaForm = document.getElementById('reservaForm');
    const cantidadAcompanantesInput = document.getElementById('numero_personas');
    const acompanantesContainer = document.getElementById('acompanantesContainer');
    const inventarioContainer = document.getElementById('inventarioContainer');

    const obtenerInventario = async () => {
        try {
            const response = await fetch('http://localhost:8080/inventarios/obtener');
            const inventario = await response.json();

            inventarioContainer.innerHTML = '';
            inventario.forEach(item => {
                const inventarioItem = document.createElement('div');
                inventarioItem.classList.add('inventario-item');
                inventarioItem.innerHTML = `
                <input type="checkbox" id="${item.equipo}" name="inventario" value="${item.idInventario}" onchange="toggleCantidadInput(this)">
                <label for="${item.equipo}">
                    ${item.equipo}
                </label>
                <div class="cantidad-container" style="display: none;">
                    <label for="cantidad_${item.idInventario}">Cantidad:</label>
                    <input type="number" id="cantidad_${item.idInventario}" name="cantidad_${item.idInventario}" min="1" style="width: 50px;">
                </div>
            `;
                inventarioContainer.appendChild(inventarioItem);
            });
        } catch (error) {
            console.error('Error al obtener el inventario:', error);
        }
    };

    function toggleCantidadInput(checkbox) {
        const cantidadContainer = checkbox.nextElementSibling.nextElementSibling;
        if (checkbox.checked) {
            cantidadContainer.style.display = 'block';
        } else {
            cantidadContainer.style.display = 'none';
            cantidadContainer.querySelector('input[type="number"]').value = '';
        }
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

    await obtenerInventario();

    cantidadAcompanantesInput.addEventListener('input', () => {
        acompanantesContainer.innerHTML = '';

        const cantidad = parseInt(cantidadAcompanantesInput.value);
        if (isNaN(cantidad) || cantidad < 0) {
            return;
        }

        for (let i = 1; i <= cantidad; i++) {
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
                <option value="" disable selected> Seleccione una carrera</option>
                <option value="1">Ingeniería de Sistemas</option>
                <option value="2">Ingeniería de Telecomunicaciones</option>
                <option value="3">Ingeniería Mecatronica</option>
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
        const cantidad = parseInt(formData.get('numero_personas'));

        for (let i = 1; i <= cantidad; i++) {
            const acompanante = {
                id_codigo: formData.get(`id_codigo_${i}`),
                nombre: formData.get(`nombre_acompanante_${i}`),
                cedula: formData.get(`cedula_acompanante_${i}`)
            };
            acompanantes.push(acompanante);
        }

        const inventarioSeleccionado = [];
        const inventarioItems = document.querySelectorAll('input[name="inventario"]:checked');
        inventarioItems.forEach(item => {
            const cantidadInput = document.getElementById(`cantidad_${item.value}`);
            const cantidad = cantidadInput ? parseInt(cantidadInput.value) : 0;
            inventarioSeleccionado.push({
                id_equipo: item.value, // ID del equipo
                nombre: item.nextElementSibling.innerText, // Nombre del equipo
                cantidad: 1 // Cantidad seleccionada
            });
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

        alert(JSON.stringify(data, null, 2));

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
