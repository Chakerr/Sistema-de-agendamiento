package laboratorio.lab.services;

import laboratorio.lab.repositories.CarrerasRepository;
import org.springframework.stereotype.Service;

@Service
public class CarrerasService {

    private CarrerasRepository carrerasRepository;

    public CarrerasService(CarrerasRepository carrerasRepository) {
        this.carrerasRepository = carrerasRepository;
    }


}
