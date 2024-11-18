package Los_Jsons.sistemas_reservas.services;

import Los_Jsons.sistemas_reservas.models.VerificarToken;
import Los_Jsons.sistemas_reservas.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public String generarToken(String email){
        String token = UUID.randomUUID().toString();
        VerificarToken verificarToken = new VerificarToken();
        verificarToken.setToken(token);
        verificarToken.setEmail(email);
        verificarToken.setExpiracionToken(LocalDateTime.now().plusMinutes(5));
        tokenRepository.save(verificarToken);
        return token;
    }

    public boolean verificarToken(String token){
        VerificarToken verificarToken = tokenRepository.buscarPorToken(token);
        if (verificarToken == null) {
            //System.out.println("Token no encontrado en la base de datos.");
            return false;
        }
        if (verificarToken.getExpiracionToken().isBefore(LocalDateTime.now())) {
            tokenRepository.eliminarToken(token);
            //System.out.println("El token ha expirado.");
            return false;
        }
        //System.out.println("Token válido y no ha expirado.");
        return true;
    }
}
