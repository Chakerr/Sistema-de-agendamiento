package laboratorio.lab.repositories;

import jakarta.transaction.Transactional;
import laboratorio.lab.models.Estudiantes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EstudiantesRepository extends JpaRepository<Estudiantes,Integer> {
    Optional<Estudiantes> findById(Integer idCodigo);
    Optional<Estudiantes> findByCodigoCarnet(String codigoCarnet);

    @Modifying
    @Transactional
    @Query("UPDATE Estudiantes e SET e.visitas = e.visitas + 1 WHERE e.id_codigo = ?1")
    int incrementarVisitas(Integer idCodigo);

    @Modifying
    @Transactional
    @Query("DELETE FROM VerificarToken vt WHERE vt.email = :correo")
    void eliminarToken(@Param("correo") String correo);
}
