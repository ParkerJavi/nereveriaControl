package chano.neveriaControl.controller;


import chano.neveriaControl.api.dto.EntradaInventarioRequest;
import chano.neveriaControl.api.dto.InventarioProductoDTO;
import chano.neveriaControl.model.almacen.InventarioProducto;
import chano.neveriaControl.service.InventarioProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario/productos")
public class InventarioProductoController {

    private final InventarioProductoService service;

    public InventarioProductoController(InventarioProductoService service) {
        this.service = service;
    }

    // POST /api/inventario/productos/{productoId}/entrada
    @PostMapping("/{productoId}/entrada")
    public ResponseEntity<InventarioProducto> entrada(@PathVariable Long productoId,
                                                      @RequestBody EntradaInventarioRequest req) {
        InventarioProducto inv = service.entrada(productoId, req.getCantidad());
        return ResponseEntity.ok(inv);
    }

    // GET /api/inventario/productos
    @GetMapping
    public ResponseEntity<List<InventarioProductoDTO>> inventarioActual() {
        return ResponseEntity.ok(service.obtenerInventarioActual());
    }



}