package laboratorio.lab.repositories;

import laboratorio.lab.models.Laboratorista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LaboratoristaRepository extends JpaRepository<Laboratorista,Integer> {
    Optional<Laboratorista> findByCorreo(String correo);
}
