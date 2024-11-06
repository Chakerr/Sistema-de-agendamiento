package laboratorio.lab.services;

import laboratorio.lab.repositories.ReservasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GerenteService {
    @Autowired
    private ReservasRepository reservasRepository;

    public GerenteService(ReservasRepository reservasRepository) {
        this.reservasRepository = reservasRepository;
    }

    public long contarReservas() {
        return reservasRepository.count();
    }

    public long obtenerReservasActivas() {
        return reservasRepository.contarReservasActivas();
    }

}