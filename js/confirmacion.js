const link = "https://ede7-191-107-128-227.ngrok-free.app"

document.addEventListener('DOMContentLoaded', () => {
    function enviarAlBackend(codigo, tipo) {
        fetch(`${link}/estudiantes/carnet`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ codigoCarnet: codigo })
        })
        .then(response => {
            if (response.ok) {
                alert("ASISTENCIA REGISTRADA");
                return response.json(); // Si la respuesta es OK, parseamos el JSON
            } else {
                alert("NO SE PUDO REGISTRAR LA ASISTENCIA");
                throw new Error('Error en la respuesta del servidor');
            }
        })
    }

    function enviarAlBackend1(codigo, tipo) {
        fetch(`${link}/estudiantes/validarCedula`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ cedula: codigo })
        })
        .then(response => {
            if (response.ok) {
                alert("ASISTENCIA REGISTRADA");
                return response.json(); // Si la respuesta es OK, parseamos el JSON
            } else {
                alert("NO SE PUDO REGISTRAR LA ASISTENCIA");
                throw new Error('Error en la respuesta del servidor');
            }
        })
    }

    document.getElementById('confirmarRFIDBtn').addEventListener('click', function() {
        let codigoRFID = document.getElementById('RFID').value;
        if (codigoRFID) {
            enviarAlBackend(codigoRFID, 'codigoRFID');
        } else {
            alert('Por favor ingresa un código RFID.');
        }
    });

    document.getElementById('confirmarAsistenciaBtn').addEventListener('click', function() {
        let cedula = document.getElementById('cedula').value;
        if (cedula) {
            enviarAlBackend1(cedula, 'cedula');
        } else {
            alert('Por favor ingresa una cédula.');
        }
    });
});