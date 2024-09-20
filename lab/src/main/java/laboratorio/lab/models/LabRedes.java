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
@Table(name="LAB_REDES")
public class LabRedes {
    //id_lab, nombre,capacidad_maxima

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String nombre;

    @Column
    private int capacidad_maxima;

    @OneToMany(mappedBy = "labRedes")
    private List<HorariosRedes> horariosRedesList;


}
