package Los_Jsons.sistemas_reservas.controllers;

import Los_Jsons.sistemas_reservas.models.Estudiantes;
import Los_Jsons.sistemas_reservas.models.VerificarToken;
import Los_Jsons.sistemas_reservas.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estudiantes")
public class EstudiantesController {
    private EstudiantesService estudiantesService;
    private AutenticationService autenticacionService;
    private EmailService emailService;
    private TokenService tokenService;
    private LaboratoristaService laboratoristaService;

    public EstudiantesController(EstudiantesService estudiantesService, AutenticationService autenticacionService, EmailService emailService, TokenService tokenService, LaboratoristaService laboratoristaService) {
        this.estudiantesService = estudiantesService;
        this.autenticacionService = autenticacionService;
        this.emailService = emailService;
        this.tokenService = tokenService;
        this.laboratoristaService = laboratoristaService;
    }

    @GetMapping("/correo-existente/{correo}")
    public ResponseEntity<String> encontrarcorreo(@PathVariable String correo){
        if(correo.equals("laboratoristaupc@gmail.com") || correo.equals("jefelaboratistasupc@gmail.com") ||
                correo.equals("gerentelaboratistasupc@gmail.com") || correo.equals("adreservasupc@gmail.com")){
            String token = tokenService.generarToken(correo);
            emailService.sendSimpleEmail(correo, "Cambio de contraseña por Token","Su token es: \n" + token);
            return ResponseEntity.ok().build();
        }
        if(estudiantesService.encontrarCorreo(correo)){
            String token = tokenService.generarToken(correo);
            emailService.sendSimpleEmail(correo, "Cambio de contraseña por Token","Su token es: \n" + token);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/verificar-token/{token}")
    public ResponseEntity<String> verifyToken(@PathVariable String token) {
        boolean isValid = tokenService.verificarToken(token);
        if (!isValid) {
            return ResponseEntity.badRequest().body("Token inválido o expirado");
        }
        return ResponseEntity.ok("Token válido, puede proceder con el registro");
    }

    @PutMapping("/cambio-contrasena/{correo}/{contrasena}")
    public ResponseEntity<String> actualizarCodigoCarnet(@PathVariable String correo, @PathVariable String contrasena) {
        if(correo.equals("laboratoristaupc@gmail.com") || correo.equals("jefelaboratistasupc@gmail.com") ||
                correo.equals("gerentelaboratistasupc@gmail.com") || correo.equals("adreservasupc@gmail.com")){
            boolean actualizado = laboratoristaService.actualizarContrasena(correo, contrasena);
            if (actualizado) {
                return ResponseEntity.ok("Contraseña actualizada exitosamente.");
            } else {
                return ResponseEntity.badRequest().build();
            }
        }else{
            boolean actualizado = estudiantesService.actualizarContrasena(correo, contrasena);
            if (actualizado) {
                return ResponseEntity.ok("Contraseña actualizada exitosamente.");
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
    }

    @PostMapping("/save")
    private ResponseEntity<String> saveEstudiante(@RequestBody Estudiantes estudiante){
        if(!estudiantesService.encontrarCorreo(estudiante.getCorreo())) {
            estudiantesService.saveEstudiante(estudiante);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Estudiantes estudiante) {
        boolean esAutenticado = autenticacionService.autenticar(estudiante.getId_codigo(), estudiante.getContrasena());
        if (esAutenticado) {
            // emailService.sendSimpleEmail("felipetrivinogarzon@gmail.com", "Prueba","Este es el body");
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/carnet")
    public ResponseEntity<String> autenticarCarnet(@RequestBody Estudiantes estudiante){
        boolean esCarnet = estudiantesService.autenticarCarnet(estudiante.getCodigoCarnet());
        if (esCarnet) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/validarCedula")
    public ResponseEntity<String> autenticarVisitaCedula(@RequestBody Estudiantes estudiante){
        boolean esCarnet = estudiantesService.autenticarVisitaCedula(estudiante.getCedula());
        if (esCarnet) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
