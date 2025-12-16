package chano.neveriaControl.controller;


import chano.neveriaControl.api.dto.EntradaInventarioRequest;
import chano.neveriaControl.model.almacen.InventarioMateriaPrima;
import chano.neveriaControl.service.InventarioMateriaPrimaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventario/materias-primas")
public class InventarioMateriaPrimaController {

    private final InventarioMateriaPrimaService service;

    public InventarioMateriaPrimaController(InventarioMateriaPrimaService service) {
        this.service = service;
    }

    // POST /api/inventario/materias-primas/{materiaPrimaId}/entrada
    @PostMapping("/{materiaPrimaId}/entrada")
    public ResponseEntity<InventarioMateriaPrima> entrada(@PathVariable Long materiaPrimaId,
                                                          @RequestBody EntradaInventarioRequest req) {
        InventarioMateriaPrima inv = service.entrada(materiaPrimaId, req.getCantidad());
        return ResponseEntity.ok(inv);
    }
}
