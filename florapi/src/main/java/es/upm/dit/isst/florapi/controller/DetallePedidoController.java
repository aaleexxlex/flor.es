package es.upm.dit.isst.florapi.controller;

import es.upm.dit.isst.florapi.model.DetallePedido;
import es.upm.dit.isst.florapi.repository.DetallePedidoRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/detallesPedido")
public class DetallePedidoController {

    private final DetallePedidoRepository detallePedidoRepository;

    public DetallePedidoController(DetallePedidoRepository detallePedidoRepository) {
        this.detallePedidoRepository = detallePedidoRepository;
    }

    // Obtener todos los detalles de pedido
    @GetMapping
    public List<DetallePedido> obtenerTodos() {
        return detallePedidoRepository.findAll();
    }

    // Obtener un detalle de pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<DetallePedido> obtenerPorId(@PathVariable Long id) {
        Optional<DetallePedido> detallePedido = detallePedidoRepository.findById(id);
        return detallePedido.map(ResponseEntity::ok)
                            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo detalle de pedido
    @PostMapping
    public ResponseEntity<DetallePedido> crear(@RequestBody DetallePedido detallePedido) {
        DetallePedido nuevoDetalle = detallePedidoRepository.save(detallePedido);
        return ResponseEntity.ok(nuevoDetalle);
    }

    // Actualizar un detalle de pedido
    @PutMapping("/{id}")
    public ResponseEntity<DetallePedido> actualizar(@PathVariable Long id, @RequestBody DetallePedido detallePedido) {
        if (!detallePedidoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        detallePedido.setIdDetalle(id); // Asegurar que el ID coincide
        DetallePedido actualizado = detallePedidoRepository.save(detallePedido);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar un detalle de pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!detallePedidoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        detallePedidoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

