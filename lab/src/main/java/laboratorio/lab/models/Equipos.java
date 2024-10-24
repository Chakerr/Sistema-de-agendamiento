package laboratorio.lab.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "EQUIPOS")
public class Equipos {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_equipo;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "CANTIDAD")
    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "id_reserva",nullable = false)
    private Reservas reserva;

    public Integer getId_equipo() {
        return id_equipo;
    }

    public void setId_equipo(Integer id_equipo) {
        this.id_equipo = id_equipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Reservas getReserva() {
        return reserva;
    }

    public void setReserva(Reservas reserva) {
        this.reserva = reserva;
    }
}
