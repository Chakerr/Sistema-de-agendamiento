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
@Table(name="HORARIOS_INV_REDES")
public class HorariosInvRedes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "HORA")
    private Time hora;

    @Column(name = "NAME")
    private String name;

    @Column(name = "FECHA")
    private Date fecha;

    @Column(name = "CANTIDAD_PARCIAL")
    private int cantidadParcial;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getCantidadParcial() {
        return cantidadParcial;
    }

    public void setCantidadParcial(int cantidadParcial) {
        this.cantidadParcial = cantidadParcial;
    }
}
