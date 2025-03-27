package es.upm.dit.isst.florapi.controller;

import es.upm.dit.isst.florapi.model.Pedido;
import es.upm.dit.isst.florapi.repository.PedidoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;

    public PedidoController(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    // Get all pedidos
    @GetMapping
    public List<Pedido> getAllPedidos() {
        return pedidoRepository.findAll();
    }

    // Get a pedido by ID
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getPedidoById(@PathVariable Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        return pedido.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new pedido
    @PostMapping
    public ResponseEntity<Pedido> createPedido(@RequestBody Pedido pedido) {
        if (pedido.getCliente() == null) {
            return ResponseEntity.badRequest().body("El campo 'cliente' es obligatorio.");
        }
        if (pedido.getEstado() == null) {
            return ResponseEntity.badRequest().body("El campo 'estado' es obligatorio.");
        }
        if (pedido.getLineasPedido() == null || pedido.getLineasPedido().isEmpty()) {
            return ResponseEntity.badRequest().body("El pedido debe contener al menos una línea.");
        }
        return ResponseEntity.ok(pedidoRepository.save(pedido));
    }

    // Update an existing pedido
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> updatePedido(@PathVariable Long id, @RequestBody Pedido pedidoDetails) {
        if (pedidoDetails.getCliente() == null) {
            return ResponseEntity.badRequest().body("El campo 'cliente' es obligatorio.");
        }
        if (pedidoDetails.getEstado() == null) {
            return ResponseEntity.badRequest().body("El campo 'estado' es obligatorio.");
        }
        return pedidoRepository.findById(id).map(pedido -> {
            pedido.setFecha(pedidoDetails.getFecha());
            pedido.setEstado(pedidoDetails.getEstado());
            pedido.setDestino(pedidoDetails.getDestino());
            pedido.setCliente(pedidoDetails.getCliente());
            pedido.setFloricultor(pedidoDetails.getFloricultor());
            pedido.setValoracion(pedidoDetails.getValoracion());
            return ResponseEntity.ok(pedidoRepository.save(pedido));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
    

    // Delete a pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePedido(@PathVariable Long id) {
        return pedidoRepository.findById(id).map(pedido -> {
            pedidoRepository.delete(pedido);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
