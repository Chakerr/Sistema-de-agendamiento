package laboratorio.lab.repositories;

import jakarta.transaction.Transactional;
import laboratorio.lab.models.Reservas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface ReservasRepository extends JpaRepository<Reservas,Integer> {

    @Modifying
    @Transactional
    @Query("SELECT t FROM Reservas t WHERE t.fecha = :currentDate AND :currentTime BETWEEN t.hora_inicio AND t.hora_fin AND t.estado = false")
    List<Reservas> reservaEncontrada(@Param("currentDate") Date currentDate, @Param("currentTime") Time currentTime);

    @Modifying
    @Transactional
    @Query("SELECT e.id_codigo FROM Reservas r JOIN r.estudiantesList e WHERE r.id = :idReserva")
    List<Integer> encontrarEstudiantesPorReserva(@Param("idReserva") Integer idReserva);

    @Modifying
    @Transactional
    @Query("UPDATE Reservas r SET r.estado = :estado WHERE r.id = :idReserva")
    int actualizarEstadoReserva(@Param("estado") boolean estado, @Param("idReserva") Integer idReserva);
}
