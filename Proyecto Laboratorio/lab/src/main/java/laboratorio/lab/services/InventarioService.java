package laboratorio.lab.services;

import laboratorio.lab.models.Inventarios;
import laboratorio.lab.repositories.InventariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioService {
    @Autowired
    private InventariosRepository inventariosRepository;

    public InventarioService(InventariosRepository inventariosRepository) {
        this.inventariosRepository = inventariosRepository;
    }

    public List<Inventarios> obtenerInventarioCompleto(){
        return inventariosRepository.findAll();
    }
}
