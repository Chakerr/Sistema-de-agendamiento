package laboratorio.lab.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "RESERVAS")
public class Reservas {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "FECHA")
    private Date fecha;

    @Column
    private Time hora_inicio;

    @Column
    private int horas;

    @Column
    private Time hora_fin;

    @Column
    private int numero_personas;

    @ManyToOne
    @JoinColumn(name = "ID_AREAESTUDIO")
    private AreaEstudios id_areaEstudio;



    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "RESERVAS_EST",
            joinColumns = @JoinColumn(name = "id_reserva", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_estudiante", referencedColumnName = "id_codigo")
    )
    private List<Estudiantes> estudiantesList;

    @ManyToMany
    @JoinTable(
            name = "HORARIOS_RESERVAS",
            joinColumns = @JoinColumn(name = "id_reserva",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_horario",referencedColumnName = "id")
    )
    private List<HorariosRedes>horariosRedesList;
}
