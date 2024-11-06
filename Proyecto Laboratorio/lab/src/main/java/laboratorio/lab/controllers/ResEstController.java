package laboratorio.lab.controllers;

import laboratorio.lab.dtos.EstudiantesDto;
import laboratorio.lab.exceptions.ExceptionReserva;
import laboratorio.lab.models.Equipos;
import laboratorio.lab.models.Estudiantes;
import laboratorio.lab.models.Reservas;
import laboratorio.lab.services.AutenticationService;
import laboratorio.lab.services.EmailService;
import laboratorio.lab.services.EstudiantesService;
import laboratorio.lab.services.ReservasService;
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

    public ResEstController(EstudiantesService estudiantesService, AutenticationService autenticacionService, ReservasService reservasService, EmailService emailService) {
        this.estudiantesService = estudiantesService;
        this.autenticacionService = autenticacionService;
        this.reservasService = reservasService;
        this.emailService = emailService;
    }

    @PostMapping("/saveEst")
    private Estudiantes saveEstudiante(@RequestBody Estudiantes estudiante){
        return estudiantesService.saveEstudiante(estudiante);
    }

    @PostMapping("/saveS")
    private List<EstudiantesDto> saveEstudiantes(@RequestBody List<Estudiantes> estudiantesList){
        return estudiantesService.saveEstudiantes(estudiantesList);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Estudiantes estudiante) {

        boolean esAutenticado = autenticacionService.autenticar(estudiante.getId_codigo(), estudiante.getContrasena());
        if (esAutenticado) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/loginNoEst")
    public ResponseEntity<String> loginNoEst(@RequestBody Estudiantes estudiante) {

        boolean esAutenticado = autenticacionService.autenticar(estudiante.getCedula(), estudiante.getContrasena());
        if (esAutenticado) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
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
                    "Laboratorio: Redes y Procesadores\n";
            if (!nuevaReserva.getEquiposList().isEmpty()){
                detalles += "Detalles de equipo(s): \n";
                for (Equipos e : nuevaReserva.getEquiposList()){
                    detalles += "Nombre de equipo: " + e.getNombre() + ", Cantidad solicitada: " + e.getCantidad() + "\n";
                }
            }
            emailService.sendSimpleEmail(correo,"Nueva Reserva Agendada.",detalles);
            if (!nuevaReserva.getEstudiantesList().isEmpty()){
                detalles += "Estudiantes: \n";
                for (Estudiantes e : nuevaReserva.getEstudiantesList()){
                    detalles += "Nombre: " + e.getNombre() + ", Código: " + e.getId_codigo() + "\n";
                }
            }
            emailService.sendSimpleEmail("laboratoristaupc@gmail.com","Nueva Reserva Agendada.",detalles);
            return ResponseEntity.ok(nuevaReserva);
        }else if (reservasService.isBool()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NO HAY CAPACIDAD DISPONIBLE EN EL LABORATORIO");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NO HAY CAPACIDAD DISPONIBLE DE Inventario " + reservasService.getEquipo());
        }
    }

    @DeleteMapping("/borrarRes/{id}")
    public void borrarReserva(@PathVariable Integer id){
        reservasService.borrarReserva(id);
    }
}
