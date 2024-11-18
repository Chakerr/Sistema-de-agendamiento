package Los_Jsons.sistemas_reservas.services;

import Los_Jsons.sistemas_reservas.models.Inventarios;
import Los_Jsons.sistemas_reservas.repositories.InventariosRepository;
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
