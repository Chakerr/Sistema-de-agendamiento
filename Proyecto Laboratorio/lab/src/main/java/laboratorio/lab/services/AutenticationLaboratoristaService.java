package laboratorio.lab.services;

import laboratorio.lab.models.Laboratorista;
import laboratorio.lab.repositories.LaboratoristaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticationLaboratoristaService {

    private LaboratoristaRepository laboratoristaRepository;

    public AutenticationLaboratoristaService(LaboratoristaRepository laboratoristaRepository) {
        this.laboratoristaRepository = laboratoristaRepository;
    }

    public boolean autenticar(String correo, String contrasena) {
        Optional<Laboratorista> laboratorista = laboratoristaRepository.findByCorreo(correo);
        if (laboratorista.isPresent()) {
            Laboratorista laboratorista1 = laboratorista.get();
            return laboratorista1.getContrasena().equals(contrasena);
        }
        return false;
    }
}
