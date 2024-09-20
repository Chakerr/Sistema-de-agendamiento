package laboratorio.lab.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "CARRERAS")
public class Carreras {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "CARRERA")
    private String carrera;

    @OneToMany(mappedBy = "id_carrera")
    @JsonManagedReference
    private List<Estudiantes> estudiantesList;

}
