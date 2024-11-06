package laboratorio.lab.controllers;

import laboratorio.lab.models.Estudiantes;
import laboratorio.lab.services.AutenticationService;
import laboratorio.lab.services.EmailService;
import laboratorio.lab.services.EstudiantesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estudiantes")
public class EstudiantesController {

    private EstudiantesService estudiantesService;
    private AutenticationService autenticacionService;
    private EmailService emailService;

    @Autowired
    public EstudiantesController(EstudiantesService estudiantesService, AutenticationService autenticacionService, EmailService emailService) {
        this.estudiantesService = estudiantesService;
        this.autenticacionService = autenticacionService;
        this.emailService = emailService;
    }

    @PostMapping("/save")
    private Estudiantes saveEstudiante(@RequestBody Estudiantes estudiante){
        return estudiantesService.saveEstudiante(estudiante);
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
