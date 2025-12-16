package chano.neveriaControl.service;


import chano.neveriaControl.model.almacen.InventarioMateriaPrima;
import chano.neveriaControl.model.catalogo.MateriaPrima;
import chano.neveriaControl.repository.InventarioMateriaPrimaRepository;
import chano.neveriaControl.repository.MateriaPrimaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class InventarioMateriaPrimaService {

    private final InventarioMateriaPrimaRepository invRepo;
    private final MateriaPrimaRepository materiaRepo;

    public InventarioMateriaPrimaService(InventarioMateriaPrimaRepository invRepo,
                                         MateriaPrimaRepository materiaRepo) {
        this.invRepo = invRepo;
        this.materiaRepo = materiaRepo;
    }

    @Transactional
    public InventarioMateriaPrima entrada(Long materiaPrimaId, BigDecimal cantidad) {
        if (cantidad == null || cantidad.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Cantidad debe ser mayor a 0");
        }

        MateriaPrima mp = materiaRepo.findById(materiaPrimaId)
                .orElseThrow(() -> new IllegalArgumentException("Materia prima no existe: " + materiaPrimaId));

        InventarioMateriaPrima inv = invRepo.findByMateriaPrimaId(materiaPrimaId)
                .orElseGet(() -> {
                    InventarioMateriaPrima nuevo = new InventarioMateriaPrima();
                    nuevo.setMateriaPrima(mp);
                    nuevo.setCantidad(BigDecimal.ZERO);
                    return nuevo;
                });

        inv.setCantidad(inv.getCantidad().add(cantidad));
        return invRepo.save(inv);
    }
}