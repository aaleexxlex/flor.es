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
    public Pedido createPedido(@RequestBody Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    // Update an existing pedido
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> updatePedido(@PathVariable Long id, @RequestBody Pedido pedidoDetails) {
        return pedidoRepository.findById(id).map(pedido -> {
            pedido.setFecha(pedidoDetails.getFecha());
            pedido.setEstado(pedidoDetails.getEstado());
            pedido.setTotal(pedidoDetails.getTotal());
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
