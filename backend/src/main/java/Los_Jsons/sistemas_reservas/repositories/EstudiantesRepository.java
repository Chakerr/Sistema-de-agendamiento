package Los_Jsons.sistemas_reservas.repositories;

import Los_Jsons.sistemas_reservas.models.Estudiantes;
import Los_Jsons.sistemas_reservas.models.Reservas;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface EstudiantesRepository extends JpaRepository<Estudiantes,Integer> {
    Optional<Estudiantes> findById(Integer idCodigo);
    List<Estudiantes> findAll();

    @Query("SELECT e FROM Estudiantes e WHERE e.codigoCarnet = :codigoCarnet")
    Optional<Estudiantes> findByCodigoCarnet(@Param("codigoCarnet") String codigoCarnet);

    @Query("SELECT e FROM Estudiantes e WHERE e.cedula = :cedula")
    Optional<Estudiantes> findByCedula(@Param("cedula") int cedula);

    @Modifying
    @Transactional
    @Query("UPDATE Estudiantes e SET e.visitas = e.visitas + 1 WHERE e.id_codigo = ?1")
    int incrementarVisitas(Integer idCodigo);

    @Modifying
    @Transactional
    @Query("DELETE FROM VerificarToken vt WHERE vt.email = :correo")
    void eliminarToken(@Param("correo") String correo);

    @Query("SELECT r FROM Reservas r JOIN r.estudiantesList e WHERE e.id_codigo = :idCodigo AND r.estado = false")
    List<Reservas> findActiveReservationsByStudent(Integer idCodigo);

    @Query("SELECT SUM(e.visitas) FROM Estudiantes e")
    Integer totalVisitas();

    @Query("SELECT e FROM Estudiantes e WHERE e.id_codigo = :idCodigo")
    Estudiantes findByIdCodigo(@Param("idCodigo") Integer idCodigo);

    @Query("SELECT e FROM Estudiantes e WHERE e.cedula = :cedula")
    Estudiantes findByIdCedula(@Param("cedula") Integer cedula);

    @Query("SELECT e FROM Estudiantes e WHERE e.correo = :correo")
    Estudiantes findByCorreo(@Param("correo") String correo);
}
