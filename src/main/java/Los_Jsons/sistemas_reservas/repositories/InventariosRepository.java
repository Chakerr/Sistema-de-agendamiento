package Los_Jsons.sistemas_reservas.repositories;

import Los_Jsons.sistemas_reservas.models.Inventarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InventariosRepository extends JpaRepository<Inventarios,Integer> {
    Inventarios findByEquipo(String name);

    @Query(value = "SELECT EQUIPO FROM INVENTARIO", nativeQuery = true)
    List<String> equipos();

    @Query(value = "SELECT cantidad FROM INVENTARIO WHERE equipo=:name", nativeQuery = true)
    Integer cantidad(@Param("name") String name);
}
