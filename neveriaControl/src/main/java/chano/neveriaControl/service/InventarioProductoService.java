package chano.neveriaControl.service;


import chano.neveriaControl.api.dto.InventarioProductoDTO;
import chano.neveriaControl.model.Enums.UnidadMedida;
import chano.neveriaControl.model.almacen.InventarioProducto;
import chano.neveriaControl.model.catalogo.Producto;
import chano.neveriaControl.repository.InventarioProductoRepository;
import chano.neveriaControl.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class InventarioProductoService {

    private final InventarioProductoRepository invRepo;
    private final ProductoRepository productoRepo;

    public InventarioProductoService(InventarioProductoRepository invRepo, ProductoRepository productoRepo) {
        this.invRepo = invRepo;
        this.productoRepo = productoRepo;
    }

    @Transactional
    public InventarioProducto entrada(Long productoId, BigDecimal cantidad) {
        validarCantidad(cantidad);

        Producto producto = productoRepo.findById(productoId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no existe: " + productoId));

        validarCantidadSegunUnidad(producto.getUnidad(), cantidad);

        InventarioProducto inv = invRepo.findByProductoId(productoId)
                .orElseGet(() -> {
                    InventarioProducto nuevo = new InventarioProducto();
                    nuevo.setProducto(producto);
                    nuevo.setCantidad(BigDecimal.ZERO);
                    return nuevo;
                });

        inv.setCantidad(inv.getCantidad().add(cantidad));
        return invRepo.save(inv);
    }

    private void validarCantidad(BigDecimal cantidad) {
        if (cantidad == null || cantidad.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Cantidad debe ser mayor a 0");
        }
    }

    private void validarCantidadSegunUnidad(UnidadMedida unidad, BigDecimal cantidad) {
        boolean debeSerEntera = (unidad == UnidadMedida.PIEZA || unidad == UnidadMedida.BOLA);
        if (debeSerEntera && cantidad.stripTrailingZeros().scale() > 0) {
            throw new IllegalArgumentException("La unidad " + unidad + " requiere cantidad entera (ej: 1,2,3).");
        }
    }

    public List<InventarioProductoDTO> obtenerInventarioActual() {
        return invRepo.obtenerInventarioActual();
    }


}
