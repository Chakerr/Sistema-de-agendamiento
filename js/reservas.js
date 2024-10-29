document.addEventListener('DOMContentLoaded', async () => {
    const reservaForm = document.getElementById('reservaForm');
    const cantidadAcompanantesInput = document.getElementById('numero_personas'); // Cambiado aquí
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
                    <input type="checkbox" id="${item.equipo}" name="inventario" value="${item.idInventario}">
                    <label for="${item.equipo}">
                        ${item.equipo}
                    </label>
                `;
                inventarioContainer.appendChild(inventarioItem);
            });
        } catch (error) {
            console.error('Error al obtener el inventario:', error);
        }
    };

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
                <h3>Acompañante ${i}</h3>
                <label for="nombre_acompanante_${i}">Nombre:</label>
                <input type="text" id="nombre_acompanante_${i}" name="nombre_acompanante_${i}" required>
                
                <label for="cedula_acompanante_${i}">Codigo:</label>
                <input type="text" id="cedula_acompanante_${i}" name="cedula_acompanante_${i}" required>
            `;

            acompanantesContainer.appendChild(div);
        }
    });

    function ajustarHoraExacta(input) {
        const [hora, minuto] = input.value.split(':').map(Number);
        if (minuto !== 0) {
            input.value = `${hora.toString().padStart(2, '0')}:00`;
        }
    }

    document.getElementById('reservaForm').addEventListener('submit', (e) => {
        const horaInicio = document.getElementById('hora_inicio').value;
        const [hora, minutos] = horaInicio.split(':').map(Number);
        const horaTotal = hora * 60 + minutos;

        // Convertir las horas de inicio y fin en minutos
        const inicioValido = 6 * 60; // 6 AM
        const finValido = 20 * 60;    // 8 PM

        if (horaTotal < inicioValido || horaTotal >= finValido) {
            e.preventDefault(); // Evitar el envío del formulario
            alert('Por favor, seleccione una hora entre 6 AM y 8 PM.');
        }
    });

    // Asignar la función al evento 'change' de los campos de hora
    document.getElementById('hora_inicio').addEventListener('change', (e) => ajustarHoraExacta(e.target));

    reservaForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const formData = new FormData(reservaForm);
        const acompanantes = [];
        const cantidad = parseInt(formData.get('numero_personas'));

        for (let i = 1; i <= cantidad; i++) {
            const acompanante = {
                nombre: formData.get(`nombre_acompanante_${i}`),
                cedula: formData.get(`cedula_acompanante_${i}`),
                carrera: formData.get(`carrera_acompanante_${i}`)
            };
            acompanantes.push(acompanante);
        }

        const inventarioSeleccionado = [];
        const inventarioItems = document.querySelectorAll('input[name="inventario"]:checked');
        inventarioItems.forEach(item => {
            inventarioSeleccionado.push(item.value);
        });

        const fecha = formData.get('fecha');
        const [day, month, year] = fecha.split('/');
        const formattedDate = fecha ? fecha.split('/').reverse().join('-') : new Date().toISOString().split('T')[0];

        const horaInicio = formData.get('hora_inicio');
        const horasSumar = parseInt(formData.get('Horas'), 10);

        const [hora, minutos] = horaInicio.split(':').map(Number);
        const fechaHoraInicio = new Date(`${formattedDate}T${horaInicio}`);
        fechaHoraInicio.setHours(fechaHoraInicio.getHours() + horasSumar);

        const horasFin = `${String(fechaHoraInicio.getHours()).padStart(2, '0')}:${String(fechaHoraInicio.getMinutes()).padStart(2, '0')}`;

        const data = {
            fecha: formattedDate,
            hora_inicio: horaInicio + ":00",
            horas: horasSumar,
            horas_fin: horasFin + ":00",
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
            const response = await fetch('http://localhost:8080/reservas', {
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