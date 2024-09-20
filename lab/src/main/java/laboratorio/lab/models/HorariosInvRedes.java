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
@Table(name="HORARIOS_INV_REDES")
public class HorariosInvRedes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private Time hora;

    @Column
    private Date fecha;

    @Column
    private int cantidadParcial;

    @ManyToOne
    @JoinColumn(name = "Id_Inventario")
    private Inventarios inventario;
}