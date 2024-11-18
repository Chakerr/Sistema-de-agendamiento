package Los_Jsons.sistemas_reservas.controllers;

import Los_Jsons.sistemas_reservas.models.Laboratorista;
import Los_Jsons.sistemas_reservas.models.Reservas;
import Los_Jsons.sistemas_reservas.services.AutenticationLaboratoristaService;
import Los_Jsons.sistemas_reservas.services.LaboratoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/administradores")
public class LaboratoristaController {
    private AutenticationLaboratoristaService autenticationLaboratoristaService;
    private LaboratoristaService laboratoristaService;

    @Autowired
    public LaboratoristaController(AutenticationLaboratoristaService autenticationLaboratoristaService, LaboratoristaService laboratoristaService) {
        this.autenticationLaboratoristaService = autenticationLaboratoristaService;
        this.laboratoristaService = laboratoristaService;
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

    @GetMapping("/reservas-activas/{idCodigo}")
    public List<Reservas> obtenerReservaPorEstudiante(@PathVariable Integer idCodigo) {
        return laboratoristaService.ReservasporEstudiante(idCodigo);
    }

    @GetMapping("/reservas-fecha/{fecha}")
    public List<Reservas> obtenerReservaPorFecha(@PathVariable Date fecha) {
        return laboratoristaService.ReservasporFecha(fecha);
    }

    @GetMapping("/total-visitas")
    public Integer obtenerTotalVisitas() {
        return laboratoristaService.obtenerTotalVisitas();
    }

    @GetMapping("/visitas/{idCodigo}")
    public Integer obtenerVisitasPorCodigo(@PathVariable Integer idCodigo) {
        return laboratoristaService.obtenerVisitasPorCodigo(idCodigo);
    }

    @GetMapping("/reservas")
    public List<Reservas> obtenerTodasLasReservas() {
        return laboratoristaService.obtenerTodasLasReservas();
    }

    @GetMapping("/total-reservas")
    public long contarReservas() {
        return laboratoristaService.contarReservas();
    }
}
