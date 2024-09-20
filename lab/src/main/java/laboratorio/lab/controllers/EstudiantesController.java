package laboratorio.lab.controllers;

import laboratorio.lab.models.Estudiantes;
import laboratorio.lab.services.AutenticationService;
import laboratorio.lab.services.EstudiantesService;
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

    public EstudiantesController(EstudiantesService estudiantesService,AutenticationService autenticacionService) {
        this.estudiantesService = estudiantesService;
        this.autenticacionService = autenticacionService;
    }

    @PostMapping("/save")
    private Estudiantes saveEstudiante(@RequestBody Estudiantes estudiante){
        System.out.println(estudiante.getContrasena());
        return estudiantesService.saveEstudiante(estudiante);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Estudiantes estudiante) {

        boolean esAutenticado = autenticacionService.autenticar(estudiante.getId_codigo(), estudiante.getContrasena());
        if (esAutenticado) {
            return ResponseEntity.ok("Login exitoso");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }
}
