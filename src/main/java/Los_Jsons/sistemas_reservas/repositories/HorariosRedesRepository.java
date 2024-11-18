package Los_Jsons.sistemas_reservas.repositories;

import Los_Jsons.sistemas_reservas.models.HorariosRedes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.sql.Time;

public interface HorariosRedesRepository extends JpaRepository<HorariosRedes,Integer> {
    @Query(value = "SELECT * FROM HORARIOS_REDES WHERE HORA = :hora AND FECHA = :fecha", nativeQuery = true)
    HorariosRedes findHorariosLab(@Param("hora") Time hora, @Param("fecha") Date fecha);
}
