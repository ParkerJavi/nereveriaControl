package chano.neveriaControl.api.dto;

import java.math.BigDecimal;

public class EntradaInventarioRequest {
    private BigDecimal cantidad; // cantidad a sumar (debe ser > 0)

    public BigDecimal getCantidad() { return cantidad; }
    public void setCantidad(BigDecimal cantidad) { this.cantidad = cantidad; }
}