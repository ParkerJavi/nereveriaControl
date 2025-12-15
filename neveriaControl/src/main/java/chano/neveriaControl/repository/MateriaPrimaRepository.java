package chano.neveriaControl.repository;

import chano.neveriaControl.model.almacen.MateriaPrima;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MateriaPrimaRepository extends JpaRepository<MateriaPrima, Long> {
    Optional<MateriaPrima> findBySku(String sku);
}