package laboratorio.lab.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;


@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "HORARIOS_REDES")
public class HorariosRedes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private Time hora;

    @Column
    private Date fecha;

    @Column
    private int capacidadParcial;

    @ManyToOne
    @JoinColumn(name = "id_lab")
    private LabRedes labRedes;
}