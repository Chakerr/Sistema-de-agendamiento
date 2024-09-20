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
@Table(name = "AREA_INVENTARIO")
public class AreaInventario {

    @EmbeddedId
    private AreaInvId id;

    @ManyToOne
    @MapsId("id_inventario")
    @JoinColumn(name = "ID_INVENTARIO")
    private Inventarios id_inventario;

    @ManyToOne
    @MapsId("id_area")
    @JoinColumn(name = "ID_AREA")
    private AreaEstudios id_area;

    @Column(name = "CANTIDAD_DESTINADA")
    private int cantidad_destinada;
}
