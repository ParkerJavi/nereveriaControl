package chano.neveriaControl.service;


import chano.neveriaControl.model.Enums.UnidadMedida;
import chano.neveriaControl.model.almacen.InventarioProducto;
import chano.neveriaControl.model.catalogo.Producto;
import chano.neveriaControl.model.ventas.Venta;
import chano.neveriaControl.model.ventas.VentaDetalle;
import chano.neveriaControl.repository.InventarioProductoRepository;
import chano.neveriaControl.repository.ProductoRepository;
import chano.neveriaControl.repository.VentaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;
    private final InventarioProductoRepository inventarioProductoRepository;

    public VentaService(VentaRepository ventaRepository,
                        ProductoRepository productoRepository,
                        InventarioProductoRepository inventarioProductoRepository) {
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
        this.inventarioProductoRepository = inventarioProductoRepository;
    }

    @Transactional
    public Venta registrarVenta(Venta venta, List<VentaDetalle> detalles) {

        BigDecimal total = BigDecimal.ZERO;

        for (VentaDetalle d : detalles) {
            Producto p = productoRepository.findById(d.getProducto().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no existe: " + d.getProducto().getId()));

            validarCantidadSegunUnidad(p.getUnidad(), d.getCantidad());

            // precio actual (puedes permitir override si lo deseas)
            d.setProducto(p);
            d.setPrecioUnitario(p.getPrecioVenta());

            BigDecimal subtotal = d.getPrecioUnitario()
                    .multiply(d.getCantidad())
                    .subtract(d.getDescuento() == null ? BigDecimal.ZERO : d.getDescuento());

            d.setSubtotal(subtotal.setScale(2, RoundingMode.HALF_UP));
            total = total.add(d.getSubtotal());

            // Descontar inventario
            InventarioProducto inv = inventarioProductoRepository.findByProductoId(p.getId())
                    .orElseThrow(() -> new IllegalStateException("No hay inventario registrado para producto: " + p.getNombre()));

            BigDecimal nuevaCantidad = inv.getCantidad().subtract(d.getCantidad());
            if (nuevaCantidad.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalStateException("Stock insuficiente para " + p.getNombre()
                        + ". Stock: " + inv.getCantidad() + ", requerido: " + d.getCantidad());
            }
            inv.setCantidad(nuevaCantidad);
            inventarioProductoRepository.save(inv);

            venta.addDetalle(d);
        }

        venta.setTotal(total.setScale(2, RoundingMode.HALF_UP));
        return ventaRepository.save(venta);
    }

    private void validarCantidadSegunUnidad(UnidadMedida unidad, BigDecimal cantidad) {
        if (cantidad == null || cantidad.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Cantidad inválida");
        }

        // Estas unidades deben ser ENTERAS (sin decimales)
        boolean debeSerEntera = (unidad == UnidadMedida.PIEZA || unidad == UnidadMedida.BOLA);

        if (debeSerEntera && cantidad.stripTrailingZeros().scale() > 0) {
            throw new IllegalArgumentException("La unidad " + unidad + " requiere cantidad entera (ej: 1,2,3).");
        }
    }
}
