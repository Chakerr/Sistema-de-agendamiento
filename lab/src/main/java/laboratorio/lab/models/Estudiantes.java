package laboratorio.lab.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonBackReference(value = "relacion1")
    private Carreras id_carrera;

//    @ManyToMany(mappedBy = "estudiantesList")
//    @JsonManagedReference(value = "relacionEstudianteReservas")
//    private List<Reservas> reservasList;

    @ManyToMany(mappedBy = "estudiantesList") // Cambia esto para que coincida con la propiedad en Reservas
    //@JsonManagedReference(value = "relacionEstudianteReservas") // Parte gestionada
    @JsonIgnore
    private List<Reservas> reservasList;

    @Column(name = "CODIGO_CARNET")
    private String codigoCarnet;

    public void setId_codigo(Integer id_codigo) {
        this.id_codigo = id_codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public int getVisitas() {
        return visitas;
    }

    public void setVisitas(int visitas) {
        this.visitas = visitas;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Carreras getId_carrera() {
        return id_carrera;
    }

    public void setId_carrera(Carreras id_carrera) {
        this.id_carrera = id_carrera;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public List<Reservas> getReservasList() {
        return reservasList;
    }

    public void setReservasList(List<Reservas> reservasList) {
        this.reservasList = reservasList;
    }

    public Integer getId_codigo() {
        return id_codigo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getCodigoCarnet() {
        return codigoCarnet;
    }

    public void setCodigoCarnet(String codigoCarnet) {
        this.codigoCarnet = codigoCarnet;
    }
}
