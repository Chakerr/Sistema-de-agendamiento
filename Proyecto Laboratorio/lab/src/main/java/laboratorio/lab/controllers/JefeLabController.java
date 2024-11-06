package laboratorio.lab.controllers;

import laboratorio.lab.models.Estudiantes;
import laboratorio.lab.models.Inventarios;
import laboratorio.lab.models.Laboratorista;
import laboratorio.lab.services.AutenticationLaboratoristaService;
import laboratorio.lab.services.AutenticationService;
import laboratorio.lab.services.JefeLabService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/JefeL")
public class JefeLabController {
    private AutenticationService autenticacionService;
    private JefeLabService jefeLabService;
    private AutenticationLaboratoristaService autenticationLaboratoristaService;

    public JefeLabController(AutenticationService autenticacionService, JefeLabService jefeLabService, AutenticationLaboratoristaService autenticationLaboratoristaService) {
        this.autenticacionService = autenticacionService;
        this.jefeLabService = jefeLabService;
        this.autenticationLaboratoristaService = autenticationLaboratoristaService;
    }

    @PostMapping("/abastecer")
    public void abastecer(@RequestBody Inventarios inv){
        System.out.println(inv.getEquipo());
        jefeLabService.abastecerInv(inv.getEquipo(),inv.getCantidad());
    }

    @GetMapping("/stats/{fecha}")
    public HashMap<String,Double> statsDia(@PathVariable String fecha){
        Date sqlDate = Date.valueOf(fecha.trim());
        return jefeLabService.statsDia(sqlDate);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Laboratorista jefeLab) {

        boolean esAutenticado = autenticationLaboratoristaService.autenticar(jefeLab.getCorreo(), jefeLab.getContrasena());
        if (esAutenticado) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}