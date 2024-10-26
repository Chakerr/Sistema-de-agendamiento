package laboratorio.lab.controllers;

import laboratorio.lab.models.Laboratorista;
import laboratorio.lab.services.AutenticationLaboratoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LaboratoristaController {

    private AutenticationLaboratoristaService autenticationLaboratoristaService;

    @Autowired
    public LaboratoristaController(AutenticationLaboratoristaService autenticationLaboratoristaService) {
        this.autenticationLaboratoristaService = autenticationLaboratoristaService;
    }

    @PostMapping("/loginLab")
    public ResponseEntity<String> autenticarCarnet(@RequestBody Laboratorista laboratorista){
        boolean esCarnet = autenticationLaboratoristaService.autenticar(laboratorista.getCorreo(), laboratorista.getContrasena());
        if (esCarnet) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
