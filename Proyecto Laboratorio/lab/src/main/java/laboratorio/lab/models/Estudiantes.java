package laboratorio.lab.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "CODIGO_CARNET")
    private String codigoCarnet;

    @ManyToOne
    @JoinColumn(name = "ID_CARRERA")
    @JsonBackReference(value = "carrera-estudiantes")
    private Carreras id_carrera;

    @ManyToMany(mappedBy = "estudiantesList")
    @JsonIgnore
    private List<Reservas> reservasList;

    public Integer getId_codigo() {
        return id_codigo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getCodigoCarnet() {return codigoCarnet;}

    public String getCorreo() {return correo;}

    public String getNombre() {return nombre;}

    public int getCedula() {return cedula;}

    public int getVisitas() {return visitas;}

    public Carreras getId_carrera() {return id_carrera;}

    public List<Reservas> getReservasList() {return reservasList;}

    public void setId_codigo(Integer id_codigo) {this.id_codigo = id_codigo;}

    public void setNombre(String nombre) {this.nombre = nombre;}

    public void setCedula(int cedula) {this.cedula = cedula;}

    public void setVisitas(int visitas) {this.visitas = visitas;}

    public void setCorreo(String correo) {this.correo = correo;}

    public void setContrasena(String contrasena) {this.contrasena = contrasena;}

    public void setCodigoCarnet(String codigoCarnet) {this.codigoCarnet = codigoCarnet;}

    public void setId_carrera(Carreras id_carrera) {this.id_carrera = id_carrera;}

    public void setReservasList(List<Reservas> reservasList) {this.reservasList = reservasList;}
}
