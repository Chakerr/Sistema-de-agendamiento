package laboratorio.lab.controllers;

import laboratorio.lab.dtos.EstudiantesDto;
import laboratorio.lab.exceptions.ExceptionReserva;
import laboratorio.lab.models.Estudiantes;
import laboratorio.lab.models.Reservas;
import laboratorio.lab.services.AutenticationService;
import laboratorio.lab.services.EstudiantesService;
import laboratorio.lab.services.ReservasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ResEst")
public class ResEstController {
    private EstudiantesService estudiantesService;
    private AutenticationService autenticacionService;
    private ReservasService reservasService;

    public ResEstController(EstudiantesService estudiantesService, AutenticationService autenticacionService, ReservasService reservasService) {
        this.estudiantesService = estudiantesService;
        this.autenticacionService = autenticacionService;
        this.reservasService = reservasService;
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

    @GetMapping("/reservas")
    public List<Reservas> verReservas(){
        return reservasService.verReservas();
    }

    @PostMapping("/SaveRes")
    public Reservas crearReserva(@RequestBody Reservas request) throws ExceptionReserva {
        System.out.println(request);
        Reservas nuevaReserva = reservasService.crearReserva(
                request,
                request.getEstudiantesList(),
                request.getId_areaEstudio().getIdArea()
        );
        return nuevaReserva;
    }

    @DeleteMapping("/borrarRes/{id}")
    public Reservas borrarReserva(@PathVariable Integer id){
        return reservasService.borrarReserva(id);
    }
}
