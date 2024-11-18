package Los_Jsons.sistemas_reservas.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ESTUDIANTES")
public class Estudiantes {
    @Id
    private Integer id_codigo;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "CEDULA", unique = true)
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

    @ManyToMany(mappedBy = "estudiantesList"
    )
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estudiantes that = (Estudiantes) o;
        return cedula == that.cedula && visitas == that.visitas && Objects.equals(id_codigo, that.id_codigo) && Objects.equals(nombre, that.nombre) && Objects.equals(correo, that.correo) && Objects.equals(contrasena, that.contrasena) && Objects.equals(id_carrera, that.id_carrera) && Objects.equals(reservasList, that.reservasList) && Objects.equals(codigoCarnet, that.codigoCarnet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_codigo, nombre, cedula, visitas, correo, contrasena, id_carrera, reservasList, codigoCarnet);
    }

    @Override
    public String toString() {
        return "Estudiantes{" +
                "id_codigo=" + id_codigo +
                ", nombre='" + nombre + '\'' +
                ", cedula=" + cedula +
                ", visitas=" + visitas +
                ", correo='" + correo + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", id_carrera=" + id_carrera +
                ", reservasList=" + reservasList +
                ", codigoCarnet='" + codigoCarnet + '\'' +
                '}';
    }
}
