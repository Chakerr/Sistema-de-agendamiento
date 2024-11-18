package Los_Jsons.sistemas_reservas.services;

import Los_Jsons.sistemas_reservas.models.Inventarios;
import Los_Jsons.sistemas_reservas.repositories.HorariosInvRedesRepository;
import Los_Jsons.sistemas_reservas.repositories.InventariosRepository;
import Los_Jsons.sistemas_reservas.repositories.LaboratoristaRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class JefeLabService {
    private LaboratoristaRepository laboratoristaRepository;
    private InventariosRepository inventariosRepository;
    private HorariosInvRedesRepository horariosInvRedesRepository;

    public JefeLabService(LaboratoristaRepository laboratoristaRepository,
                          InventariosRepository inventariosRepository,
                          HorariosInvRedesRepository horariosInvRedesRepository) {
        this.laboratoristaRepository = laboratoristaRepository;
        this.inventariosRepository = inventariosRepository;
        this.horariosInvRedesRepository = horariosInvRedesRepository;
    }

    public void abastecerInv(String name, Integer cantidad){
        Inventarios inv = inventariosRepository.findByEquipo(name);
        inv.setCantidad(inv.getCantidad()+cantidad);
        inventariosRepository.save(inv);
    }

    public HashMap<String,Double> statsDia(Date dia){
        // Select Sum(cantidad_parcial) from horarios_inv_redes where fecha =:fecha and name =:name
        List<String> arr = inventariosRepository.equipos();
        System.out.println(arr.size());
        HashMap<String, Double> hash = new HashMap<>();
        int temp,cant;
        double res;
        String name;
        for (int i = 0; i < arr.size(); i++) {
            name = arr.get(i);
            System.out.println(name);
            temp = horariosInvRedesRepository.sumar(dia,name);
            cant = inventariosRepository.cantidad(name) * 16;
            res = (double) temp * 100 / cant;
            hash.put(name,res);
        }
        System.out.println(hash);
        return hash;
    }
}
