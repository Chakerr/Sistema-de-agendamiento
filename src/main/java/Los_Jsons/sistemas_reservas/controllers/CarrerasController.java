package Los_Jsons.sistemas_reservas.controllers;

import Los_Jsons.sistemas_reservas.services.CarrerasService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarrerasController {
    private CarrerasService carrerasService;

    public CarrerasController(CarrerasService carrerasService) {
        this.carrerasService = carrerasService;
    }
}
