package laboratorio.lab.repositories;

import jakarta.transaction.Transactional;
import laboratorio.lab.models.VerificarToken;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.function.Function;

public interface TokenRepository extends JpaRepository<VerificarToken, Integer> {
    @Query("SELECT vt FROM VerificarToken vt WHERE vt.token = :token")
    VerificarToken buscarPorToken(@Param("token") String token);

    @Modifying
    @Transactional
    @Query("DELETE FROM VerificarToken WHERE token = :token")
    void eliminarToken(@Param("token") String token);
}
