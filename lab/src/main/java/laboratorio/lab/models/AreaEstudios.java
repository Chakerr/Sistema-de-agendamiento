package laboratorio.lab.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="AREA_ESTUDIO")
public class AreaEstudios {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idArea;

    @Column
    private String area;

    @OneToMany(mappedBy = "id_area")
    private List<AreaInventario> areaInventarioList;
//    @ManyToMany(mappedBy = "areaEstudiosList")
//    private List<Inventarios> inventarioList;


    @OneToMany(mappedBy = "id_areaEstudio")
    private List<Reservas> reservasList;
}