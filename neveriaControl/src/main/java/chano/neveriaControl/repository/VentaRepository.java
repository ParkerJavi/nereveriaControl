package chano.neveriaControl.repository;


import chano.neveriaControl.model.ventas.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Long> {}