package laboratorio.lab.repositories;

import jakarta.transaction.Transactional;
import laboratorio.lab.models.Equipos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EquiposRepository extends JpaRepository<Equipos,Integer> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM EQUIPOS WHERE id_reserva = :id", nativeQuery = true)
    void deleteId(@Param("id") int id);
}
