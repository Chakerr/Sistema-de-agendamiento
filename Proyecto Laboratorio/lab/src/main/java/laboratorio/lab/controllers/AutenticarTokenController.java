package laboratorio.lab.controllers;

import laboratorio.lab.models.Estudiantes;
import laboratorio.lab.models.VerificarToken;
import laboratorio.lab.services.EmailService;
import laboratorio.lab.services.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutenticarTokenController {

    private TokenService tokenService;
    private EmailService emailService;

    public AutenticarTokenController(TokenService tokenService, EmailService emailService) {
        this.tokenService = tokenService;
        this.emailService = emailService;
    }

    @PostMapping("/pre-register")
    public ResponseEntity<String> preRegister(@RequestBody Estudiantes estudiante) {
        String token = tokenService.generarToken(estudiante.getCorreo());
        emailService.sendSimpleEmail(estudiante.getCorreo(), "Verificación Por Token","Su token es: \n" + token);
        System.out.println("Token enviado: " + token);
        return ResponseEntity.ok("Token de verificación enviado al correo");
    }

    @PostMapping("/verify-token")
    public ResponseEntity<String> verifyToken(@RequestBody VerificarToken token) {
        boolean isValid = tokenService.verificarToken(token.getToken());
        if (!isValid) {
            return ResponseEntity.badRequest().body("Token inválido o expirado");
        }
        return ResponseEntity.ok("Token válido, puede proceder con el registro");
    }
}
