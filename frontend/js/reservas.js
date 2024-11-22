const link = "https://sistema-agendamiento-1-back-472b7073b8ab.herokuapp.com"
document.addEventListener('DOMContentLoaded', async () => {
    const reservaForm = document.getElementById('reservaForm');
    const cantidadAcompanantesInput = document.getElementById('numero_personas');
    const acompanantesContainer = document.getElementById('acompanantesContainer');

    const numeroPersonasInput = document.getElementById('numero_personas');

    numeroPersonasInput.addEventListener('input', function () {
        const value = parseInt(numeroPersonasInput.value, 10);

        if (value < 1 || value > 24) {
            alert("La cantidad de asistentes debe ser entre 1 y 24.");
            numeroPersonasInput.value = 0; // Restaura el valor a 1 si está fuera de rango
        }
    });


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

                data.sort((a, b) => {
                    const equipoA = a.equipo.toLowerCase();
                    const equipoB = b.equipo.toLowerCase();

                    if (equipoA < equipoB) return -1;
                    if (equipoA > equipoB) return 1;
                    return 0; // Si son iguales
                });

                const table = document.createElement('table');
                const headerRow = document.createElement('tr');
                headerRow.innerHTML = '<th>ID</th><th>Equipo</th><th>Disponible</th><th>Seleccionar cantidad</th>';
                table.appendChild(headerRow);

                data.forEach((inventario) => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td class="id-cuadro">${inventario.idInventario}</td>
                        <td class="nombre-equipo">${inventario.equipo}</td>
                        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${inventario.cantidad}</td>
                        <td>
                            <input type="number" 
                                   min="1" 
                                   max="${inventario.cantidad}" 
                                   id="cantidad_${inventario.idInventario}"
                                   data-id="${inventario.idInventario}" 
                                   data-nombre="${inventario.equipo}" 
                                   data-inventario="true"
                                   oninput="verificarCantidad(this,${inventario.cantidad})" />
                        </td>
                    `;
                    table.appendChild(row);
                });
                resultContainer.appendChild(table);
            })
            .catch(error => console.error('Error:', error));
    }

    // Establecer la fecha mínima a mañana en hora local
    const fechaInput = document.getElementById('fecha');
    const hoy = new Date();
    hoy.setDate(hoy.getDate() + 1); // Sumar 1 día a la fecha actual

    // Obtener la fecha en el formato YYYY-MM-DD en la zona horaria local
    const año = hoy.getFullYear();
    const mes = String(hoy.getMonth() + 1).padStart(2, '0'); // Los meses empiezan desde 0
    const dia = String(hoy.getDate()).padStart(2, '0');

    const formatoFecha = `${año}-${mes}-${dia}`;
    fechaInput.min = formatoFecha;

    fechaInput.addEventListener('change', (event) => {
        const fechaSeleccionada = new Date(event.target.value);
        const diaSemana = fechaSeleccionada.getDay();

        // Obtener la fecha actual
        const hoy = new Date();

        // Calcular la fecha que está en 8 días (es decir, el próximo miércoles)
        const ochoDias = new Date(hoy);
        ochoDias.setDate(hoy.getDate() + 8);

        // Comprobar si la fecha seleccionada es dentro de los próximos 8 días
        if (fechaSeleccionada < hoy || fechaSeleccionada > ochoDias) {
            alert('Por favor, seleccione una fecha dentro de los próximos 8 días.');
            fechaInput.value = '';
            fechaInput.focus();
            return; // Salir de la función si la fecha no está dentro del rango permitido
        }

        // Comprobar si el día seleccionado es un domingo
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
                <h3>Asistente ${i}</h3>
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
            const idField = div.querySelector(`#id_codigo_${i}`);

            nombreField.addEventListener('input', (e) => {
                // Reemplaza cualquier carácter que no sea letra, espacio
                nombreField.value = nombreField.value.replace(/[^a-zA-Z\s]/g, '');
            });
            idField.addEventListener('input', () => {
                idField.value = idField.value.replace(/[^0-9]/g, '').slice(0, 10);
            });
            idField.addEventListener('blur', () => {
                if (idField.value.length < 7) {
                    alert(`El código o cédula debe tener al menos 7 dígitos.`);
                    idField.focus();
                }
            });
        }
    });



    reservaForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const formData = new FormData(reservaForm);

        const idCodigo1 = formData.get('id_codigo_1');
        const storedId = sessionStorage.getItem('id');

        if (idCodigo1 !== storedId) {
            alert("Tiene que ingresar el código de la persona que hace la reserva en el primer acompañante");
            return;
        }

        const acompanantes = [];
        const cantidadacom = parseInt(formData.get('numero_personas'));
        const boton = document.getElementById('miBoton');
        boton.disabled = true;
        boton.show = false;
        alert("Se está procesando su solicitud, por favor espere unos segundos");

        for (let i = 1; i <= cantidadacom; i++) {
            const acompanante = {
                id_codigo: formData.get(`id_codigo_${i}`),
                nombre: formData.get(`nombre_acompanante_${i}`),
                cedula: formData.get(`cedula_acompanante_${i}`)
            };
            acompanantes.push(acompanante);
        }

        const inventarioSeleccionado = [];
        let totalInventarioSeleccionado = 0;

        document.querySelectorAll('input[type="number"][data-inventario="true"]').forEach(input => {
            const cantidadsele = parseInt(input.value);
            if (cantidadsele > 0) {
                totalInventarioSeleccionado += cantidadsele;
                inventarioSeleccionado.push({
                    id_equipo: input.getAttribute('data-id'),
                    nombre: input.getAttribute('data-nombre'),
                    cantidad: cantidadsele
                });
            }
        });

        // Verificar si el total de inventario excede el límite permitido
        const maxInventarioPorAsistente = cantidadacom * 3;
        if (totalInventarioSeleccionado > maxInventarioPorAsistente) {
            alert("No se puede seleccionar más de 3 ítems por asistente. Para seleccionar más, realice la solicitud una vez ingrese a su práctica.");
            boton.disabled = false;
            boton.show = true;
            return;
        }

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

        try {
            const response = await fetch(`${link}/ResEst/SaveRes`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data),
            });

            if (response.ok) {
                const responseBody = await response.json(); // Obtén el cuerpo de la respuesta en formato JSON
                alert(`Reserva creada con éxito: se han enviado los detalles al correo electronico`);
                reservaForm.reset();
                acompanantesContainer.innerHTML = '';
                window.location.href = 'Usuario.html';
            } else {
                const errorBody = await response.text(); // Obtén el cuerpo de la respuesta en caso de error
                alert(`Error al crear la reserva: ${errorBody}`);
                boton.disabled = false;
                boton.show = true;
            }
        } catch (error) {
            console.error('Error al crear la reserva');
        }
    });


});
document.addEventListener("DOMContentLoaded", function () {
    // Verificar si 'id' está almacenado en sessionStorage
    if (!sessionStorage.getItem('id')) {
        // Si no existe, redirigir a inicio.html
        alert('No tienes permiso para acceder a esta página.');
        window.location.href = 'index.html'; // Cambia a la ruta correspondiente de tu página de inicio
    }
}); 