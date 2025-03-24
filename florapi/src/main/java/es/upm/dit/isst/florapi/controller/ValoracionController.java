package es.upm.dit.isst.florapi.controller;

import es.upm.dit.isst.florapi.model.Valoracion;
import es.upm.dit.isst.florapi.repository.ValoracionRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

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
        Valoracion nuevaValoracion = valoracionRepository.save(valoracion);
        return ResponseEntity.ok(nuevaValoracion);
    }

    // Actualizar una valoración existente
    @PutMapping("/{id}")
    public ResponseEntity<Valoracion> actualizar(@PathVariable Long id, @RequestBody Valoracion valoracion) {
        if (!valoracionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        valoracion.setIdValoracion(id); // Asegurar que el ID coincide
        Valoracion actualizada = valoracionRepository.save(valoracion);
        return ResponseEntity.ok(actualizada);
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
