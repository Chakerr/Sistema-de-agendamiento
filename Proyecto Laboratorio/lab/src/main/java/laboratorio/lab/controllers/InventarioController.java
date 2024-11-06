package laboratorio.lab.controllers;

import laboratorio.lab.models.Inventarios;
import laboratorio.lab.services.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/inventarios")
public class InventarioController {
    @Autowired
    private InventarioService inventariosService;

    public InventarioController(InventarioService inventariosService) {
        this.inventariosService = inventariosService;
    }

    @GetMapping("/obtener")
    public List<Inventarios> obtenerInventario() {
        return inventariosService.obtenerInventarioCompleto();
    }
}
