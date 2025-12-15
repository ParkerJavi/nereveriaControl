package chano.neveriaControl.model.almacen;

import chano.neveriaControl.model.catalogo.MateriaPrima;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "inventario_materias_primas",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_materia_prima", columnNames = "materia_prima_id")
)
public class InventarioMateriaPrima {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "materia_prima_id", nullable = false)
    private MateriaPrima materiaPrima;

    @Column(name = "cantidad", nullable = false, precision = 12, scale = 3)
    private BigDecimal cantidad = BigDecimal.ZERO;

    @Column(name = "actualizado_en", nullable = false)
    private LocalDateTime actualizadoEn;

    @PrePersist
    @PreUpdate
    void touch() {
        this.actualizadoEn = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public MateriaPrima getMateriaPrima() { return materiaPrima; }
    public void setMateriaPrima(MateriaPrima materiaPrima) { this.materiaPrima = materiaPrima; }

    public BigDecimal getCantidad() { return cantidad; }
    public void setCantidad(BigDecimal cantidad) { this.cantidad = cantidad; }

    public LocalDateTime getActualizadoEn() { return actualizadoEn; }
}