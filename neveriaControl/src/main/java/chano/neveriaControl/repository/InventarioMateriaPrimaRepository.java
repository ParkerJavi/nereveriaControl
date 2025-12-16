package chano.neveriaControl.repository;

import chano.neveriaControl.api.dto.InventarioMateriaPrimaDTO;
import chano.neveriaControl.model.almacen.InventarioMateriaPrima;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InventarioMateriaPrimaRepository extends JpaRepository<InventarioMateriaPrima, Long> {
    Optional<InventarioMateriaPrima> findByMateriaPrimaId(Long materiaPrimaId);
    @Query("""
    select new chano.neveriaControl.api.dto.InventarioMateriaPrimaDTO(
        mp.id, mp.nombre, cast(mp.unidad as string), inv.cantidad, mp.stockMinimo
    )
    from InventarioMateriaPrima inv
    join inv.materiaPrima mp
    where mp.activo = true
    order by mp.nombre asc
""")
    List<InventarioMateriaPrimaDTO> obtenerInventarioActual();
}