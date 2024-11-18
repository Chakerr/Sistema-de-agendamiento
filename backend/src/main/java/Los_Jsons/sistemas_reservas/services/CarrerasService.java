package Los_Jsons.sistemas_reservas.services;

import Los_Jsons.sistemas_reservas.repositories.CarrerasRepository;
import org.springframework.stereotype.Service;

@Service
public class CarrerasService {
    private CarrerasRepository carrerasRepository;

    public CarrerasService(CarrerasRepository carrerasRepository) {
        this.carrerasRepository = carrerasRepository;
    }
}
