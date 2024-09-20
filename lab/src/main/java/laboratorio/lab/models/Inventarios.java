package laboratorio.lab.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="INVENTARIO")
public class Inventarios {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idInventario;

    @Column
    private String equipo;

    @Column
    private int cantidad;

    @Column
    private String marca;

    @Column
    private String placa;

    @OneToMany(mappedBy = "inventario")
    private List<HorariosInvRedes> horarios = new ArrayList<>();

    @OneToMany(mappedBy = "id_inventario")
    private List<AreaInventario> areaInventarioList;
//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "AREAS_INVENTARIO",
//            joinColumns = @JoinColumn(name = "id_inventario", referencedColumnName = "idInventario"),
//            inverseJoinColumns = @JoinColumn(name = "id_Area", referencedColumnName = "idArea")
//    )
//    private List <AreaEstudios> areaEstudiosList;



}
