package Los_Jsons.sistemas_reservas.repositories;

import Los_Jsons.sistemas_reservas.models.Equipos;
import jakarta.transaction.Transactional;
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
