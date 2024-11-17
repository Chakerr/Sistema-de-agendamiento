const link = "https://ede7-191-107-128-227.ngrok-free.app"
// Función para eliminar un estudiante
function eliminarEstudiante() {
    const id = document.getElementById('eliminarId').value;
    fetch(`${link}/jsons/estudiantes/${id}`, {
        method: 'DELETE'
    })
    .then(response => {
        if (response.ok) {
            alert('Estudiante eliminado exitosamente');
        } else {
            alert('Error al eliminar estudiante');
        }
    })
    .catch(error => console.error('Error:', error));
}

// Función para modificar el código del carnet de un estudiante
function modificarEstudiante() {
    const id = document.getElementById('modificarId').value;
    const nuevoCodigo = document.getElementById('nuevoCodigoCarnet').value;

    fetch(`${link}/jsons/estudiantes/${id}/codigoCarnet`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ codigoCarnet: nuevoCodigo })
    })
    .then(response => {
        if (response.ok) {
            alert('Código de carnet modificado exitosamente');
        } else {
            alert('Error al modificar el código de carnet');
        }
    })
    .catch(error => console.error('Error:', error));
}
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
                row.innerHTML =` 
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