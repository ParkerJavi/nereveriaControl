package chano.neveriaControl.repository;

import chano.neveriaControl.api.dto.InventarioProductoDTO;
import chano.neveriaControl.model.almacen.InventarioProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InventarioProductoRepository extends JpaRepository<InventarioProducto, Long> {
    Optional<InventarioProducto> findByProductoId(Long productoId);
    @Query("""
        select new chano.neveriaControl.api.dto.InventarioProductoDTO(
            p.id, p.nombre, cast(p.unidad as string), inv.cantidad, p.precioVenta
        )
        from InventarioProducto inv
        join inv.producto p
        where p.activo = true
        order by p.nombre asc
    """)
    List<InventarioProductoDTO> obtenerInventarioActual();


}