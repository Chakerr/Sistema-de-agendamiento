package laboratorio.lab.controllers;

import laboratorio.lab.dtos.EstudiantesDto;
import laboratorio.lab.exceptions.ExceptionReserva;
import laboratorio.lab.models.Estudiantes;
import laboratorio.lab.models.Reservas;
import laboratorio.lab.services.EstudiantesService;
import laboratorio.lab.services.ReservaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ResEst")
public class ResEstController {
    private EstudiantesService estudiantesService;
    private ReservaService reservasService;

    public ResEstController(EstudiantesService estudiantesService, ReservaService reservasService) {
        this.estudiantesService = estudiantesService;
        this.reservasService = reservasService;
    }

    @PostMapping("/saveS")
    private List<EstudiantesDto> saveEstudiantes(@RequestBody List<Estudiantes> estudiantesList){
        return estudiantesService.saveEstudiantes(estudiantesList);
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
}
