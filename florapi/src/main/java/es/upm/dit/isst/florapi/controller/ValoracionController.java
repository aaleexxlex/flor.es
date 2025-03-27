package es.upm.dit.isst.florapi.controller;

import es.upm.dit.isst.florapi.model.Valoracion;
import es.upm.dit.isst.florapi.repository.ValoracionRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/valoraciones")
public class ValoracionController {

    private final ValoracionRepository valoracionRepository;

    public ValoracionController(ValoracionRepository valoracionRepository) {
        this.valoracionRepository = valoracionRepository;
    }

    // Obtener todas las valoraciones
    @GetMapping
    public List<Valoracion> obtenerTodas() {
        return valoracionRepository.findAll();
    }

    // Obtener una valoración por ID
    @GetMapping("/{id}")
    public ResponseEntity<Valoracion> obtenerPorId(@PathVariable Long id) {
        Optional<Valoracion> valoracion = valoracionRepository.findById(id);
        return valoracion.map(ResponseEntity::ok)
                         .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva valoración
    @PostMapping
    public ResponseEntity<Valoracion> crear(@RequestBody Valoracion valoracion) {
        // Validación básica: no se puede crear sin pedido ni calificaciones
        if (valoracion.getPedido() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        if (valoracion.getCalificacionPedido() < 0 || valoracion.getCalificacionLogistica() < 0) {
            return ResponseEntity.badRequest().body(null);
        }

        Valoracion nuevaValoracion = valoracionRepository.save(valoracion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaValoracion);
    }

    // Actualizar una valoración existente
    @PutMapping("/{id}")
    public ResponseEntity<Valoracion> actualizar(@PathVariable Long id, @RequestBody Valoracion valoracion) {
        return valoracionRepository.findById(id).map(v -> {
            v.setCalificacionPedido(valoracion.getCalificacionPedido());
            v.setCalificacionLogistica(valoracion.getCalificacionLogistica());
            v.setComentario(valoracion.getComentario());
            v.setFecha(valoracion.getFecha());
            // No se cambia el pedido, ya que es relación 1:1 y debe mantenerse fija
            valoracionRepository.save(v);
            return ResponseEntity.ok(v);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Eliminar una valoración
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!valoracionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        valoracionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
