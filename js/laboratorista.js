function leerRFID() {
    let codigoGenerado = 'RFID_' + Math.random().toString(36).substr(2, 9);
    return codigoGenerado;
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
    let reservas = [
        { id: 1, usuario: 'Juan Pérez', fecha: '2024-10-01', laboratorio: 'Lab 1' },
        { id: 2, usuario: 'Ana Gómez', fecha: '2024-10-02', laboratorio: 'Lab 2' }
    ];

    let listaReservas = document.getElementById('listaReservas');
    listaReservas.innerHTML = '';
    reservas.forEach(reserva => {
        let reservaDiv = document.createElement('div');
        reservaDiv.textContent = `Usuario: ${reserva.usuario} - Fecha: ${reserva.fecha} - Laboratorio: ${reserva.laboratorio}`;
        listaReservas.appendChild(reservaDiv);
    });
}

function cargarMateriales() {
    let materiales = [
        { id: 1, nombre: 'Microscopio', cantidad: 5 },
        { id: 2, nombre: 'Proyector', cantidad: 2 }
    ];

    let listaMateriales = document.getElementById('listaMateriales');
    materiales.forEach(material => {
        let materialDiv = document.createElement('div');
        materialDiv.textContent = `Material: ${material.nombre} - Cantidad: ${material.cantidad}`;
        listaMateriales.appendChild(materialDiv);
    });
}

// Función para consultar el número de visitas de un estudiante
document.getElementById('consultarVisitasBtn').addEventListener('click', function() {
    const codigoEstudiante = document.getElementById('codigoEstudianteVisitas').value;

    // Simulación de una consulta al backend para obtener el número de visitas
    const numeroVisitas = Math.floor(Math.random() * 10); // Simulando visitas
    document.getElementById('resultadoVisitas').textContent = `Número de visitas del estudiante ${codigoEstudiante}: ${numeroVisitas}`;

    // Calcular y mostrar el total de visitas (simulación)
    const totalVisitas = 50; // Cambia esto a la lógica real que necesites
    document.getElementById('totalVisitas').textContent = `Número total de visitas: ${totalVisitas}`;
});

// Nueva función para consultar reservas de un estudiante
document.getElementById('consultarReservasBtn').addEventListener('click', function() {
    const codigoEstudiante = document.getElementById('codigoEstudianteReservas').value;
    // Simulación de reservas
    const reservasEstudiante = [
        { fecha: '2024-10-01', laboratorio: 'Lab 1' },
        { fecha: '2024-10-02', laboratorio: 'Lab 2' }
    ]; // Cambia esto por la lógica real

    const resultadoReservas = document.getElementById('resultadoReservas');
    resultadoReservas.innerHTML = ''; // Limpiar resultados anteriores
    if (reservasEstudiante.length > 0) {
        reservasEstudiante.forEach(reserva => {
            const reservaDiv = document.createElement('div');
            reservaDiv.textContent = `Fecha: ${reserva.fecha} - Laboratorio: ${reserva.laboratorio}`;
            resultadoReservas.appendChild(reservaDiv);
        });
    } else {
        resultadoReservas.textContent = `No hay reservas para el estudiante ${codigoEstudiante}.`;
    }
});

// Supongamos que estas variables obtienen los valores del servidor o una API
let totalVisitas = 120; // Cambia este valor dinámicamente
let totalReservas = 75;  // Cambia este valor dinámicamente

// Asigna los valores a los elementos del DOM
document.getElementById('numero-visitas').innerText = totalVisitas;
document.getElementById('numero-reservas').innerText = totalReservas;

// Suponiendo que tienes un arreglo de reservas
const reservas = [
    {
        id: 1,
        descripcion: "Reserva de sala de reuniones 101",
        fecha: "2024-10-28",
        hora: "10:00 AM",
        duracion: "2 horas"
    },
    {
        id: 2,
        descripcion: "Reserva de laboratorio de informática",
        fecha: "2024-10-29",
        hora: "1:00 PM",
        duracion: "3 horas"
    }
];

// Función para mostrar/ocultar los detalles de las reservas
function toggleDetalles() {
    const detallesReserva = document.getElementById('detalles-reserva');
    detallesReserva.style.display = detallesReserva.style.display === 'none' ? 'block' : 'none';

    if (detallesReserva.style.display === 'block') {
        mostrarDetalles();
    }
}

// Función para mostrar los detalles de las reservas
function mostrarDetalles() {
    const detallesReserva = document.getElementById('detalles-reserva');
    detallesReserva.innerHTML = ''; // Limpiar contenido anterior

    reservas.forEach(reserva => {
        const divReserva = document.createElement('div');
        divReserva.innerHTML = `
            <h2>Reserva ${reserva.id}</h2>
            <p><strong>Descripción:</strong> ${reserva.descripcion}</p>
            <p><strong>Fecha:</strong> ${reserva.fecha}</p>
            <p><strong>Hora:</strong> ${reserva.hora}</p>
            <p><strong>Duración:</strong> ${reserva.duracion}</p>
            <hr>
        `;
        detallesReserva.appendChild(divReserva);
    });
}


cargarReservas();
cargarMateriales();
