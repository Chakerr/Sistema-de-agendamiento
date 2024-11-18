package Los_Jsons.sistemas_reservas.mappers;

import Los_Jsons.sistemas_reservas.dtos.EstudiantesDto;
import Los_Jsons.sistemas_reservas.models.Estudiantes;
import org.springframework.stereotype.Service;

@Service
public class EstudiantesMapper {
    public EstudiantesDto EstudiantesToDto (Estudiantes estudiantes){
        var est = new EstudiantesDto(estudiantes.getNombre(),estudiantes.getCedula(),estudiantes.getId_carrera());
        return est;
    }
}
