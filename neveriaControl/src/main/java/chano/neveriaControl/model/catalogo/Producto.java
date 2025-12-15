package chano.neveriaControl.model.catalogo;

import chano.neveriaControl.model.Enums.UnidadMedida;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "productos",
        indexes = {
                @Index(name = "idx_productos_nombre", columnList = "nombre"),
                @Index(name = "idx_productos_sku", columnList = "sku", unique = true)
        })
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "sku", length = 50, unique = true)
    private String sku; // opcional: código interno o código de barras

    @Enumerated(EnumType.STRING)
    @Column(name = "unidad", nullable = false, length = 20)
    private UnidadMedida unidad;

    @Column(name = "precio_venta", nullable = false, precision = 12, scale = 2)
    private BigDecimal precioVenta;

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

    // getters & setters (o Lombok)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public UnidadMedida getUnidad() { return unidad; }
    public void setUnidad(UnidadMedida unidad) { this.unidad = unidad; }

    public BigDecimal getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(BigDecimal precioVenta) { this.precioVenta = precioVenta; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public LocalDateTime getCreadoEn() { return creadoEn; }
    public LocalDateTime getActualizadoEn() { return actualizadoEn; }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", sku='" + sku + '\'' +
                ", unidad=" + unidad +
                ", precioVenta=" + precioVenta +
                ", activo=" + activo +
                ", creadoEn=" + creadoEn +
                ", actualizadoEn=" + actualizadoEn +
                '}';
    }
}
