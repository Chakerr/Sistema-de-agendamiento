document.addEventListener('DOMContentLoaded', async () => {
    const reservaForm = document.getElementById('reservaForm');
    const cantidadAcompanantesInput = document.getElementById('numero_personas'); // Cambiado aquí
    const acompanantesContainer = document.getElementById('acompanantesContainer');
    const inventarioContainer = document.getElementById('inventarioContainer');

    const obtenerInventario = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/inventario');
            const inventario = await response.json();

            inventarioContainer.innerHTML = '';
            inventario.forEach(item => {
                const inventarioItem = document.createElement('div');
                inventarioItem.classList.add('inventario-item');
                inventarioItem.innerHTML = `
                    <input type="checkbox" id="${item.nombre}" name="inventario" value="${item.nombre}">
                    <label for="${item.nombre}">
                        <img src="${item.imagenUrl}" alt="${item.nombre}" class="inventario-img">
                        ${item.nombre} - Descripción: ${item.descripcion}
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
                
                <label for="cedula_acompanante_${i}">Cédula:</label>
                <input type="text" id="cedula_acompanante_${i}" name="cedula_acompanante_${i}" required>
                
                <label for="carrera_acompanante_${i}">Carrera:</label>
                <input type="text" id="carrera_acompanante_${i}" name="carrera_acompanante_${i}" required>
            `;

            acompanantesContainer.appendChild(div);
        }
    });

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
