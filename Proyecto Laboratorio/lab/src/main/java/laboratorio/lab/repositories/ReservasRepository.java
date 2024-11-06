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
    @Query("SELECT t FROM Reservas t WHERE t.fecha = :currentDate")
    List<Reservas> reservaEncontrada(@Param("currentDate") Date currentDate);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Reservas r JOIN r.estudiantesList e " +
            "WHERE r.id = :reservaId AND e.id_codigo = :estudianteId")
    boolean perteneceReservaAEstudiante(@Param("reservaId") Integer reservaId, @Param("estudianteId") Integer estudianteId);

    @Query("SELECT t FROM Reservas t WHERE t.fecha = :currentDate AND "
            + "t.hora_fin BETWEEN :horaFinMenos15Min AND :horaFinMas15Min")
    List<Reservas> findReservasEntreHoras(@Param("currentDate") Date currentDate,
                                          @Param("horaFinMenos15Min") Time horaFinMenos15Min,
                                          @Param("horaFinMas15Min") Time horaFinMas15Min);

    @Modifying
    @Transactional
    @Query("SELECT e.id_codigo FROM Reservas r JOIN r.estudiantesList e WHERE r.id = :idReserva")
    List<Integer> encontrarEstudiantesPorReserva(@Param("idReserva") Integer idReserva);

    @Modifying
    @Transactional
    @Query("UPDATE Reservas r SET r.estado = :estado WHERE r.id = :idReserva")
    int actualizarEstadoReserva(@Param("estado") boolean estado, @Param("idReserva") Integer idReserva);

    @Modifying
    @Transactional
    @Query("SELECT r FROM Reservas r JOIN r.estudiantesList e WHERE e.id_codigo = :idEstudiante")
    List<Reservas> findReservasByEstudianteId(@Param("idEstudiante") Integer idEstudiante);

    @Transactional
    @Modifying
    @Query( value = "DELETE FROM reservas_est WHERE id_reserva = :id", nativeQuery = true)
    void deleteRela(@Param("id") int id);

    @Query("SELECT COUNT(*) FROM Reservas")
    long contarReservas();

    @Query("SELECT COUNT(r) FROM Reservas r WHERE r.estado = true")
    long contarReservasActivas();
}
