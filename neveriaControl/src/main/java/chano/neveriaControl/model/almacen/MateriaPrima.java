package chano.neveriaControl.model.almacen;

import chano.neveriaControl.model.Enums.UnidadInsumo;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "materias_primas",
        indexes = {
                @Index(name = "idx_materias_primas_nombre", columnList = "nombre"),
                @Index(name = "idx_materias_primas_sku", columnList = "sku", unique = true)
        })
public class MateriaPrima {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "sku", length = 50, unique = true)
    private String sku; // opcional

    @Enumerated(EnumType.STRING)
    @Column(name = "unidad", nullable = false, length = 20)
    private UnidadInsumo unidad;

    @Column(name = "costo_unitario", precision = 12, scale = 2)
    private BigDecimal costoUnitario; // opcional, costo promedio/estimado

    @Column(name = "stock_minimo", precision = 12, scale = 3)
    private BigDecimal stockMinimo; // opcional, alertas

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    @Column(name = "creado_en", nullable = false)
    private LocalDateTime creadoEn;

    @Column(name = "actualizado_en", nullable = false)
    private LocalDateTime actualizadoEn;

    @PrePersist
    void onCreate() {
        var now = LocalDateTime.now();
        this.creadoEn = now;
        this.actualizadoEn = now;
    }

    @PreUpdate
    void onUpdate() {
        this.actualizadoEn = LocalDateTime.now();
    }

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public UnidadInsumo getUnidad() { return unidad; }
    public void setUnidad(UnidadInsumo unidad) { this.unidad = unidad; }

    public BigDecimal getCostoUnitario() { return costoUnitario; }
    public void setCostoUnitario(BigDecimal costoUnitario) { this.costoUnitario = costoUnitario; }

    public BigDecimal getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(BigDecimal stockMinimo) { this.stockMinimo = stockMinimo; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public LocalDateTime getCreadoEn() { return creadoEn; }
    public LocalDateTime getActualizadoEn() { return actualizadoEn; }

    @Override
    public String toString() {
        return "MateriaPrima{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", sku='" + sku + '\'' +
                ", unidad=" + unidad +
                ", costoUnitario=" + costoUnitario +
                ", stockMinimo=" + stockMinimo +
                ", activo=" + activo +
                ", creadoEn=" + creadoEn +
                ", actualizadoEn=" + actualizadoEn +
                '}';
    }
}
