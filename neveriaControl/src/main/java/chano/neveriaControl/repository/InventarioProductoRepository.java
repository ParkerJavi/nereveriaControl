package chano.neveriaControl.repository;

import chano.neveriaControl.model.almacen.InventarioProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventarioProductoRepository extends JpaRepository<InventarioProducto, Long> {
    Optional<InventarioProducto> findByProductoId(Long productoId);
}