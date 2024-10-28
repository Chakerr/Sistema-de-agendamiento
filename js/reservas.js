document.addEventListener('DOMContentLoaded', async () => {
    const reservaForm = document.getElementById('reservaForm');
    const cantidadAcompanantesInput = document.getElementById('cantidad_acompanantes');
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
                    <input type="checkbox" id="${item.nombre}" name="inventario" value="${item.nombre}" class="inventario-checkbox">
                    <label for="${item.nombre}">
                        <img src="${item.imagenUrl}" alt="${item.nombre}" class="inventario-img">
                        ${item.nombre} - Descripción: ${item.descripcion}
                    </label>
                    <label for="cantidad_${item.nombre}">Cantidad:</label>
                    <input type="number" id="cantidad_${item.nombre}" name="cantidad_${item.nombre}" value="1" min="1" style="width: 50px;" disabled>
                `;
                inventarioContainer.appendChild(inventarioItem);
            });


            const checkboxes = document.querySelectorAll('.inventario-checkbox');
            checkboxes.forEach(checkbox => {
                checkbox.addEventListener('change', (e) => {
                    const cantidadInput = document.getElementById(`cantidad_${checkbox.value}`);
                    cantidadInput.disabled = !checkbox.checked; 
                    if (checkbox.checked) {
                        cantidadInput.value = 1;
                    }
                });
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
        const cantidad = parseInt(formData.get('cantidad_acompanantes'));

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
        const data = {
            laboratorio: formData.get('laboratorio'),
            fecha: formData.get('fecha'),
            hora_inicio: formData.get('hora_inicio'),
            hora_fin: formData.get('hora_fin'),
            acompanantes: acompanantes,
            inventario: inventarioSeleccionado
        };

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
                inventarioContainer.innerHTML = '';
            } else {
                alert('Error al crear la reserva');
            }
        } catch (error) {
            console.error('Error al crear la reserva:', error);
        }
    });
});
