package Los_Jsons.sistemas_reservas.controllers;

import Los_Jsons.sistemas_reservas.dtos.EstudiantesDto;
import Los_Jsons.sistemas_reservas.exceptions.ExceptionReserva;
import Los_Jsons.sistemas_reservas.models.Equipos;
import Los_Jsons.sistemas_reservas.models.Estudiantes;
import Los_Jsons.sistemas_reservas.models.Reservas;
import Los_Jsons.sistemas_reservas.models.VerificarToken;
import Los_Jsons.sistemas_reservas.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/ResEst")
public class ResEstController {
    private EstudiantesService estudiantesService;
    private AutenticationService autenticacionService;
    private ReservasService reservasService;
    private EmailService emailService;
    private TokenService tokenService;

    public ResEstController(EstudiantesService estudiantesService, AutenticationService autenticacionService, ReservasService reservasService, EmailService emailService, TokenService tokenService) {
        this.estudiantesService = estudiantesService;
        this.autenticacionService = autenticacionService;
        this.reservasService = reservasService;
        this.emailService = emailService;
        this.tokenService = tokenService;
    }

    @GetMapping("/reservas")
    public List<Reservas> verReservas(){
        return reservasService.verReservas();
    }

    @PostMapping("/SaveRes")
    public ResponseEntity<?> crearReserva(@RequestBody Reservas request) throws ExceptionReserva {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(request.getFecha());
        calendar.add(Calendar.DAY_OF_MONTH,1);
        Date nuevaFecha = new Date(calendar.getTimeInMillis());
        request.setFecha(nuevaFecha);
        Reservas nuevaReserva = reservasService.crearReserva(
                request,
                request.getEstudiantesList(),
                request.getId_areaEstudio().getIdArea()
        );
        if (nuevaReserva != null){
            String correo = reservasService.obtenerElCorreo(nuevaReserva.getEstudiantesList().get(0).getId_codigo());
            String detalles="Se ha generado su reserva con los siguiente datos: \n" +
                    "ID de la reserva: " + nuevaReserva.getId() +"\n" +
                    "Fecha de la reserva: " + nuevaReserva.getFecha() + "\n" +
                    "Hora de inicio: " + nuevaReserva.getHora_inicio() + "\n" +
                    "Hora de Fin: " + nuevaReserva.getHora_fin() + "\n" +
                    "Laboratorio: Redes y Procesadores" + "\n";
            if (!nuevaReserva.getEquiposList().isEmpty()){
                detalles += "Detalles de equipo(s): \n";
                for (Equipos e : nuevaReserva.getEquiposList()){
                    detalles += "Nombre de equipo: " + e.getNombre() + ", Cantidad solicitada: " + e.getCantidad() + "\n";
                }
            }
            String detallesE = detalles;
            String emoji = "\uD83D\uDE42"; // 🙂

            detallesE += "\n" + "Por favor no olvidar traer la bata de laboratorio y el carnet. Muchas gracias por usar nuestro sistema. " + emoji;
            emailService.sendSimpleEmail(correo,"Nueva Reserva Agendada, ID " + nuevaReserva.getId(),detallesE);

            if (!nuevaReserva.getEstudiantesList().isEmpty()){
                detalles += "Estudiantes: \n";
                for (Estudiantes e : nuevaReserva.getEstudiantesList()){
                    detalles += "Nombre: " + e.getNombre() + ", Código: " + e.getId_codigo() + "\n";
                }
            }
            emailService.sendSimpleEmail("laboratoristaupc@gmail.com","Nueva Reserva Agendada.",detalles);
            return ResponseEntity.ok(nuevaReserva);
        }else if(reservasService.isResR()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("YA AGENDÓ UNA RESERVA PARA ESTE DIA,SELECCIONE OTRA FECHA");
        }
        else if (reservasService.isBool()) {
            reservasService.setBool(false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NO HAY CAPACIDAD DISPONIBLE EN EL LABORATORIO");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NO HAY CAPACIDAD DISPONIBLE DE Inventario " + reservasService.getEquipo());
        }
    }

    @DeleteMapping("/borrarRes/{id}")
    public ResponseEntity<String> borrarReserva(@PathVariable Integer id){
        List<Estudiantes> estudiantesReserva = reservasService.obtenerEstudiantesPorReserva(id);
        String correo = estudiantesReserva.get(0).getCorreo();
        reservasService.borrarReserva(id);
        emailService.sendSimpleEmail(correo, "Cancelación exitosa","Su reserva con id " + id + " ha sido cancelada con éxito.");
        emailService.sendSimpleEmail("laboratoristaupc@gmail.com", "Estudiante ha cancelado la reserva con id " + id,"El estudiante con código " + estudiantesReserva.get(0).getId_codigo() + " ha cancelado su reserva con id " + id);
        return ResponseEntity.ok("La cancelación ha sido exitosa.");
    }

    @GetMapping("/envio-confirmacion/{id}")
    public ResponseEntity<String> tokenCancelacion(@PathVariable Integer id){
        List<Estudiantes> estudiantesReserva = reservasService.obtenerEstudiantesPorReserva(id);
        String correo = estudiantesReserva.get(0).getCorreo();
        String token = tokenService.generarToken(correo);
        emailService.sendSimpleEmail(correo, "Verificación de Cancelación Por Token","Su token es: \n" + token);
        return ResponseEntity.ok("Token de verificación enviado al correo");
    }

    @PostMapping("/Verificar-cancelacion")
    public ResponseEntity<String> verifyToken(@RequestBody VerificarToken token) {
        boolean isValid = tokenService.verificarToken(token.getToken());
        if (!isValid) {
            return ResponseEntity.badRequest().body("Token inválido o expirado");
        }
        return ResponseEntity.ok("Token válido, puede proceder con la cancelación de la reserva");
    }

    @GetMapping("/verificar")
    public ResponseEntity<String> verificarReservaEstudiante(@RequestParam Integer reservaId, @RequestParam Integer codigoEstudiante) {
        if(reservasService.verificarReservaEstudiante(reservaId, codigoEstudiante)){
            return ResponseEntity.ok("La reserva si pertenece al estudiante, se puede proceder a cancelarla.");
        }
        return ResponseEntity.badRequest().body("La reserva no existe o no pertenece al estudiante");
    }
}
