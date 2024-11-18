package Los_Jsons.sistemas_reservas.repositories;

import Los_Jsons.sistemas_reservas.models.VerificarToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TokenRepository extends JpaRepository<VerificarToken, Integer> {
    @Query("SELECT vt FROM VerificarToken vt WHERE vt.token = :token")
    VerificarToken buscarPorToken(@Param("token") String token);

    @Modifying
    @Transactional
    @Query("DELETE FROM VerificarToken WHERE token = :token")
    void eliminarToken(@Param("token") String token);
}
