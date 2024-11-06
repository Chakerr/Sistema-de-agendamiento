package laboratorio.lab.repositories;

import laboratorio.lab.models.AreaEstudios;
import laboratorio.lab.models.HorariosInvRedes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.sql.Time;

public interface HorariosInvRedesRepository extends JpaRepository<HorariosInvRedes,Integer> {
    @Query(value = "SELECT * FROM HORARIOS_INV_REDES WHERE NAME = :name AND HORA = :hora AND FECHA = :fecha", nativeQuery = true)
    HorariosInvRedes findHorarios(@Param("name") String name, @Param("hora") Time hora, @Param("fecha") Date fecha);

    @Query(value = "SELECT SUM(cantidad_parcial) FROM horarios_inv_redes WHERE fecha =:fecha and name =:name", nativeQuery = true)
    Integer sumar(@Param("fecha") Date fecha, @Param("name") String name);
}
