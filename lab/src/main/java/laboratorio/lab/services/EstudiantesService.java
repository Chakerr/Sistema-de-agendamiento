package laboratorio.lab.services;

import laboratorio.lab.models.Estudiantes;
import laboratorio.lab.repositories.EstudiantesRepository;
import org.springframework.stereotype.Service;

@Service
public class EstudiantesService {

    private EstudiantesRepository estudiantesRepository;

    public EstudiantesService(EstudiantesRepository estudiantesRepository) {
        this.estudiantesRepository = estudiantesRepository;
    }

    public Estudiantes saveEstudiante(Estudiantes estudiante){
        return estudiantesRepository.save(estudiante);
    }
}
