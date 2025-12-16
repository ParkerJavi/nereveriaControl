package chano.neveriaControl.api.dto;

import java.math.BigDecimal;

public class InventarioProductoDTO {
    private Long productoId;
    private String nombre;
    private String unidad;
    private BigDecimal cantidad;
    private BigDecimal precioVenta;

    public InventarioProductoDTO(Long productoId, String nombre, String unidad, BigDecimal cantidad, BigDecimal precioVenta) {
        this.productoId = productoId;
        this.nombre = nombre;
        this.unidad = unidad;
        this.cantidad = cantidad;
        this.precioVenta = precioVenta;
    }



    public Long getProductoId() { return productoId; }
    public String getNombre() { return nombre; }
    public String getUnidad() { return unidad; }
    public BigDecimal getCantidad() { return cantidad; }
    public BigDecimal getPrecioVenta() { return precioVenta; }
}