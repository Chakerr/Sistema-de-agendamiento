package laboratorio.lab.models;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class AreaInvId {

    private Integer id_inventario;
    private Integer id_area;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AreaInvId areaInvId = (AreaInvId) o;
        return Objects.equals(id_inventario, areaInvId.id_inventario) && Objects.equals(id_area, areaInvId.id_area);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_inventario, id_area);
    }
}
