package laboratorio.lab.mappers;

import laboratorio.lab.dtos.EstudiantesDto;
import laboratorio.lab.models.Estudiantes;
import org.springframework.stereotype.Service;

@Service
public class EstudiantesMapper {
    public EstudiantesDto EstudiantesToDto (Estudiantes estudiantes){
        var est = new EstudiantesDto(estudiantes.getNombre(),estudiantes.getCedula(),estudiantes.getId_carrera());
        return est;
    }
}
