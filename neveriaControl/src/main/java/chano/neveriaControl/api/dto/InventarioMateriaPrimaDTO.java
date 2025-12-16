package chano.neveriaControl.api.dto;

import java.math.BigDecimal;

public class InventarioMateriaPrimaDTO {
    private Long materiaPrimaId;
    private String nombre;
    private String unidad;
    private BigDecimal cantidad;
    private BigDecimal stockMinimo;

    public InventarioMateriaPrimaDTO(Long materiaPrimaId, String nombre, String unidad, BigDecimal cantidad, BigDecimal stockMinimo) {
        this.materiaPrimaId = materiaPrimaId;
        this.nombre = nombre;
        this.unidad = unidad;
        this.cantidad = cantidad;
        this.stockMinimo = stockMinimo;
    }

    public Long getMateriaPrimaId() { return materiaPrimaId; }
    public String getNombre() { return nombre; }
    public String getUnidad() { return unidad; }
    public BigDecimal getCantidad() { return cantidad; }
    public BigDecimal getStockMinimo() { return stockMinimo; }
}