package laboratorio.lab.services;

import laboratorio.lab.dtos.EstudiantesDto;
import laboratorio.lab.mappers.EstudiantesMapper;
import laboratorio.lab.models.Estudiantes;
import laboratorio.lab.models.Reservas;
import laboratorio.lab.repositories.CarrerasRepository;
import laboratorio.lab.repositories.EstudiantesRepository;
import laboratorio.lab.repositories.ReservasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EstudiantesService {
    private EstudiantesRepository estudiantesRepository;
    private ReservasRepository reservasRepository;
    private EstudiantesMapper estudiantesMapper;
    private CarrerasRepository carrerasRepository;

    @Autowired
    public EstudiantesService(EstudiantesRepository estudiantesRepository, ReservasRepository reservasRepository, EstudiantesMapper estudiantesMapper, CarrerasRepository carrerasRepository) {
        this.estudiantesRepository = estudiantesRepository;
        this.estudiantesMapper = estudiantesMapper;
        this.carrerasRepository = carrerasRepository;
        this.reservasRepository = reservasRepository;
    }

    public Estudiantes saveEstudiante(Estudiantes estudiante){
        estudiantesRepository.eliminarToken(estudiante.getCorreo());
        return estudiantesRepository.save(estudiante);
    }

    public boolean autenticarCarnet(String codCarnet){
        Optional<Estudiantes> estudianteEnc = estudiantesRepository.findByCodigoCarnet(codCarnet);
        if (estudianteEnc.isPresent()) {
            Estudiantes estudiante = estudianteEnc.get();
            Date fechaActual = new Date(System.currentTimeMillis());
            Time horaActual = new Time(System.currentTimeMillis());
            List<Reservas> reserva = reservasRepository.reservaEncontrada(fechaActual, horaActual);
            if(!reserva.isEmpty()) {
                List<Integer> estudiantesReserva = reservasRepository.encontrarEstudiantesPorReserva(reserva.get(0).getId());
                for (Integer codigo : estudiantesReserva) {
                    estudiantesRepository.incrementarVisitas(codigo);
                }
                reservasRepository.actualizarEstadoReserva(true, reserva.get(0).getId());
                return true;
            }
            return false;
        }
        return false;
    }

    public List<EstudiantesDto> saveEstudiantes(@RequestBody List<Estudiantes> estudiantesList) {
        return estudiantesRepository.saveAll(estudiantesList).stream().map(estudiantesMapper::EstudiantesToDto).collect(Collectors.toList());
    }
}
