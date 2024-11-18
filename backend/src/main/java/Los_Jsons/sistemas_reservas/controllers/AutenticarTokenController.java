package Los_Jsons.sistemas_reservas.controllers;

import Los_Jsons.sistemas_reservas.models.Estudiantes;
import Los_Jsons.sistemas_reservas.models.VerificarToken;
import Los_Jsons.sistemas_reservas.services.EmailService;
import Los_Jsons.sistemas_reservas.services.EstudiantesService;
import Los_Jsons.sistemas_reservas.services.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutenticarTokenController {
    private TokenService tokenService;
    private EmailService emailService;
    private EstudiantesService estudiantesService;

    public AutenticarTokenController(TokenService tokenService, EmailService emailService, EstudiantesService estudiantesService) {
        this.tokenService = tokenService;
        this.emailService = emailService;
        this.estudiantesService = estudiantesService;
    }

    @PostMapping("/pre-register")
    public ResponseEntity<String> preRegister(@RequestBody Estudiantes estudiante) {
        if(!estudiantesService.encontrarCorreo(estudiante.getCorreo()) && !estudiantesService.encontrarCedula(estudiante.getCedula()) && !estudiantesService.encontrarCodigo(estudiante.getId_codigo())) {
            String token = tokenService.generarToken(estudiante.getCorreo());
            emailService.sendSimpleEmail(estudiante.getCorreo(), "Verificación Por Token", "Su token es: \n" + token);
            return ResponseEntity.ok("Token de verificación enviado al correo");
        }
        return ResponseEntity.badRequest().body("Token de verificación no fue enviado al correo, hay datos que están en uso");
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
