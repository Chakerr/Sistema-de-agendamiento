package laboratorio.lab.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "ESTUDIANTES")
public class Estudiantes {

    @Id
    private Integer id_codigo;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "CEDULA")
    private int cedula;


    @Column(name = "VISITAS")
    private int visitas;

    @Column(name = "CORREO", unique = true)
    private String correo;

    @Column(name = "CONTRASENA")
    private String contrasena;

    @ManyToOne
    @JoinColumn(name = "ID_CARRERA")
    @JsonBackReference
    private Carreras id_carrera;

    @ManyToMany(mappedBy = "estudiantesList")
    private List<Reservas> reservasList;

    public Integer getId_codigo() {
        return id_codigo;
    }

    public String getContrasena() {
        return contrasena;
    }
}
