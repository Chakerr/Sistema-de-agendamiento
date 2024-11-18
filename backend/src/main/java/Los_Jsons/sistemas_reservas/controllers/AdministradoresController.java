package Los_Jsons.sistemas_reservas.controllers;

import Los_Jsons.sistemas_reservas.models.Estudiantes;
import Los_Jsons.sistemas_reservas.models.Laboratorista;
import Los_Jsons.sistemas_reservas.services.AdministradoresService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jsons")
public class AdministradoresController {
    private AdministradoresService administradoresService;

    public AdministradoresController(AdministradoresService administradoresService) {
        this.administradoresService = administradoresService;
    }

    @PostMapping("/loginAdm")
    public ResponseEntity<String> autenticarCarnet(@RequestBody Laboratorista admin){
        boolean esCarnet = administradoresService.autenticar(admin.getCorreo(), admin.getContrasena());
        if (esCarnet) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/estudiantes")
    public List<Estudiantes> obtenerTodosLosLaboratoristas() {
        List<Estudiantes> estudiantes = administradoresService.obtenerTodosLosLaboratoristas();
        for(Estudiantes e : estudiantes){
            e.setContrasena("");
        }
        return estudiantes;
    }

    @PutMapping("/estudiantes/{idCodigo}/codigoCarnet")
    public ResponseEntity<String> actualizarCodigoCarnet(
            @PathVariable Integer idCodigo, @RequestBody Estudiantes estudiante) {
        boolean actualizado = administradoresService.actualizarCodigoCarnet(idCodigo, estudiante.getCodigoCarnet());
        if (actualizado) {
            return ResponseEntity.ok("Código de carnet actualizado exitosamente.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/estudiantes/{idCodigo}")
    public ResponseEntity<String> eliminarEstudiante(@PathVariable Integer idCodigo) {
        boolean eliminado = administradoresService.eliminarEstudiante(idCodigo);
        if (eliminado) {
            return ResponseEntity.ok("Estudiante eliminado exitosamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estudiante no encontrado.");
        }
    }
}
