document.addEventListener('DOMContentLoaded', () => {
    function leerRFID() {
        return 'RFID_' + Math.random().toString(36).substr(2, 9);
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
});
