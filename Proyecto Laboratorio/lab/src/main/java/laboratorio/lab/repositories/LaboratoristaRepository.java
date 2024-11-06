package laboratorio.lab.repositories;

import laboratorio.lab.models.Estudiantes;
import laboratorio.lab.models.Laboratorista;
import laboratorio.lab.models.Reservas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface LaboratoristaRepository extends JpaRepository<Laboratorista,Integer> {
    Optional<Laboratorista> findByCorreo(String correo);

    @Query("SELECT r FROM Reservas r WHERE r.fecha = :fecha")
    List<Reservas> reservasPorFecha(@Param("fecha") Date fecha);
}