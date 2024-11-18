package Los_Jsons.sistemas_reservas.models;

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

    @Column(name = "HORA")
    private Time hora;

    @Column(name = "FECHA")
    private Date fecha;

    @Column(name = "CAPACIDAD_PARCIAL")
    private int capacidadParcial;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getCapacidadParcial() {
        return capacidadParcial;
    }

    public void setCapacidadParcial(int capacidadParcial) {
        this.capacidadParcial = capacidadParcial;
    }
}
