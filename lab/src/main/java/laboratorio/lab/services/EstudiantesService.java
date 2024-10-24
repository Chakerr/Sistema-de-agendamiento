package laboratorio.lab.services;

import laboratorio.lab.DTO.EstudiantesDto;
import laboratorio.lab.mappers.EstudiantesMapper;
import laboratorio.lab.models.Carreras;
import laboratorio.lab.models.Estudiantes;
import laboratorio.lab.repositories.CarrerasRepository;
import laboratorio.lab.repositories.EstudiantesRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstudiantesService {

    private EstudiantesRepository estudiantesRepository;
    private EstudiantesMapper estudiantesMapper;
    private CarrerasRepository carrerasRepository;

    public EstudiantesService(EstudiantesRepository estudiantesRepository, EstudiantesMapper estudiantesMapper, CarrerasRepository carrerasRepository) {
        this.estudiantesRepository = estudiantesRepository;
        this.estudiantesMapper = estudiantesMapper;
        this.carrerasRepository = carrerasRepository;
    }

    public Estudiantes saveEstudiante(Estudiantes estudiante){
       // estudiante.getId_carrera().getEstudiantesList().add(estudiante);
        return estudiantesRepository.save(estudiante);

    }

    public List<EstudiantesDto> saveEstudiantes(@RequestBody List<Estudiantes> estudiantesList){
//        for(Estudiantes estu : estudiantesList){
//            estu.getId_carrera().getEstudiantesList().add(estu);
//            carrerasRepository.save(estu.getId_carrera());
//        }
        return estudiantesRepository.saveAll(estudiantesList).stream().map(estudiantesMapper::EstudiantesToDto).collect(Collectors.toList());
    }
}
