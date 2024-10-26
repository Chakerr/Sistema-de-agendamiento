package laboratorio.lab.models;
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
@Table(name="AREA_ESTUDIO")
public class AreaEstudios {
    @Id
    private Integer idArea;

    @Column(name = "AREA")
    private String area;


    @OneToMany(mappedBy = "id_areaEstudio")
    private List<Reservas> reservasList;

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<Reservas> getReservasList() {
        return reservasList;
    }

    public void setReservasList(List<Reservas> reservasList) {
        this.reservasList = reservasList;
    }
}