package laboratorio.lab.services;

import laboratorio.lab.models.Estudiantes;
import laboratorio.lab.repositories.EstudiantesRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticationService {
    private EstudiantesRepository estudiantesRepository;

    public AutenticationService(EstudiantesRepository estudiantesRepository) {
        this.estudiantesRepository = estudiantesRepository;
    }

    public boolean autenticar(Integer idCodigo, String contrasena) {
        Optional<Estudiantes> estudianteEnc = estudiantesRepository.findById(idCodigo);
        if (estudianteEnc.isPresent()) {
            Estudiantes estudiante = estudianteEnc.get();
            return estudiante.getContrasena().equals(contrasena);
        }
        return false;
    }
}