package laboratorio.lab.repositories;

import laboratorio.lab.models.Estudiantes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstudiantesRepository extends JpaRepository<Estudiantes,Integer> {
    Optional<Estudiantes> findById(Integer idCodigo);
}
