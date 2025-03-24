package es.upm.dit.isst.florapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import es.upm.dit.isst.florapi.model.*;
import es.upm.dit.isst.florapi.repository.*;
import java.util.List;
import org.springframework.http.ResponseEntity;
import java.net.URISyntaxException;
import org.springframework.http.HttpStatus;
import java.net.URI;
@RestController
@RequestMapping("/floricultores")
public class FloricultorController {

    @Autowired
    private FloricultorRepository floricultorRepository;

    // Obtener todos los floricultores
    @GetMapping
    public List<Floricultor> getAllFloricultores() {
        return floricultorRepository.findAll();
    }

    // Crear un nuevo floricultor
    @PostMapping
    public ResponseEntity<Floricultor> createFloricultor(@RequestBody Floricultor floricultor) throws URISyntaxException {
        if (floricultorRepository.findById(floricultor.getEmail()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Floricultor result = floricultorRepository.save(floricultor);
        return ResponseEntity.created(new URI("/floricultores/" + result.getEmail())).body(result);
    }

    // Obtener un floricultor por su email (ID como String)
    @GetMapping("/{email}")
    public ResponseEntity<Floricultor> getFloricultorByEmail(@PathVariable String email) {
        return floricultorRepository.findById(email)
            .map(floricultor -> ResponseEntity.ok().body(floricultor))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Actualizar un floricultor por email (ID como String)
    @PutMapping("/{email}")
    public ResponseEntity<Floricultor> updateFloricultor(@RequestBody Floricultor newFloricultor, @PathVariable String email) {
        return floricultorRepository.findById(email).map(floricultor -> {
            floricultor.setEmail(newFloricultor.getEmail());
            floricultor.setNombre(newFloricultor.getNombre());
            floricultor.setUbicacion(newFloricultor.getUbicacion());
            floricultor.setDisponibilidad(newFloricultor.isDisponibilidad());
            floricultorRepository.save(floricultor);
            return ResponseEntity.ok().body(floricultor);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Actualizar parcialmente un floricultor por email (ID como String)
    @PatchMapping("/{email}")
    public ResponseEntity<Floricultor> partialUpdateFloricultor(@RequestBody Floricultor newFloricultor, @PathVariable String email) {
        return floricultorRepository.findById(email).map(floricultor -> {
            if (newFloricultor.getNombre() != null) {
                floricultor.setNombre(newFloricultor.getNombre());
            }
            if (newFloricultor.getUbicacion() != null) {
                floricultor.setUbicacion(newFloricultor.getUbicacion());
            }
            floricultor.setDisponibilidad(newFloricultor.isDisponibilidad());
            floricultorRepository.save(floricultor);
            return ResponseEntity.ok().body(floricultor);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Eliminar un floricultor por email (ID como String)
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteFloricultor(@PathVariable String email) {
        floricultorRepository.deleteById(email);
        return ResponseEntity.noContent().build();
    }
}
