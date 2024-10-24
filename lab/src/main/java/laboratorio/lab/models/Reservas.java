package laboratorio.lab.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(name="HORA_INICIO")
    private Time hora_inicio;

    @Column(name = "HORAS")
    private int horas;

    @Column(name="HORA_FIN")
    private Time hora_fin;

    @Column(name="NUMERO_PERSONAS")
    private int numero_personas;

    @Column(name="ESTADO")
    private boolean estado;

    @ManyToOne
    @JoinColumn(name = "ID_AREAESTUDIO")
    private AreaEstudios id_areaEstudio;

    @OneToMany(mappedBy = "reserva")
    private List<Equipos> equiposList;

//    //@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @ManyToMany
//    @JoinTable(
//            name = "RESERVAS_EST",
//            joinColumns = @JoinColumn(name = "id_reserva", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "id_estudiante", referencedColumnName = "id_codigo")
//    )
//    //@JsonManagedReference(value = "relacion2")
//    @JsonBackReference(value = "relacionEstudianteReservas")
//    private List<Estudiantes> estudiantesList;

    @ManyToMany
    @JoinTable(
            name = "RESERVAS_EST",
            joinColumns = @JoinColumn(name = "id_reserva", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_estudiante", referencedColumnName = "id_codigo")
    )
    @JsonIgnore
    private List<Estudiantes> estudiantesList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(Time hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public Time getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(Time hora_fin) {
        this.hora_fin = hora_fin;
    }

    public int getNumero_personas() {
        return numero_personas;
    }

    public void setNumero_personas(int numero_personas) {
        this.numero_personas = numero_personas;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public AreaEstudios getId_areaEstudio() {
        return id_areaEstudio;
    }

    public void setId_areaEstudio(AreaEstudios id_areaEstudio) {
        this.id_areaEstudio = id_areaEstudio;
    }

    public List<Equipos> getEquiposList() {
        return equiposList;
    }

    public void setEquiposList(List<Equipos> equiposList) {
        this.equiposList = equiposList;
    }

    public List<Estudiantes> getEstudiantesList() {
        return estudiantesList;
    }

    public void setEstudiantesList(List<Estudiantes> estudiantesList) {
        this.estudiantesList = estudiantesList;
    }
}
