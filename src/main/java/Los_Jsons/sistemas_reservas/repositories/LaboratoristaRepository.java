package Los_Jsons.sistemas_reservas.repositories;

import Los_Jsons.sistemas_reservas.models.Estudiantes;
import Los_Jsons.sistemas_reservas.models.Laboratorista;
import Los_Jsons.sistemas_reservas.models.Reservas;
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

    @Query("SELECT e FROM Laboratorista e WHERE e.correo = :correo")
    Laboratorista findCorreo(@Param("correo") String correo);
}
