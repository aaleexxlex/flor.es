package es.upm.dit.isst.florapi.controller;

import es.upm.dit.isst.florapi.model.Producto;
import es.upm.dit.isst.florapi.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    // Obtener todos los productos
    @GetMapping
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    // Obtener un producto por ID
    @GetMapping("/{idProducto}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long idProducto) {
        return productoRepository.findById(idProducto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtener productos por email del floricultor
    @GetMapping("/floricultor/{email}")
    public ResponseEntity<List<Producto>> getProductosByFloricultorEmail(@PathVariable String email) {
        List<Producto> productos = productoRepository.findByFloricultorEmail(email);
        if (productos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productos);
    }

    // Crear un nuevo producto
    @PostMapping
    public ResponseEntity<Producto> createProducto(@RequestBody Producto newProducto) {
        Producto savedProducto = productoRepository.save(newProducto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProducto);
    }

    // Actualizar completamente un producto
    @PutMapping("/{idProducto}")
    public ResponseEntity<Producto> updateProducto(@PathVariable Long idProducto, @RequestBody Producto updatedProducto) {
        return productoRepository.findById(idProducto).map(producto -> {
            producto.setNombre(updatedProducto.getNombre());
            producto.setTipoFlor(updatedProducto.getTipoFlor());
            producto.setPrecio(updatedProducto.getPrecio());
            producto.setImagen(updatedProducto.getImagen()); // ¡AQUÍ!
            producto.setFloricultor(updatedProducto.getFloricultor());
            productoRepository.save(producto);
            return ResponseEntity.ok(producto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Actualización parcial
    @PatchMapping("/{idProducto}")
    public ResponseEntity<Producto> partialUpdateProducto(@PathVariable Long idProducto, @RequestBody Producto partialProducto) {
        return productoRepository.findById(idProducto).map(producto -> {
            if (partialProducto.getNombre() != null) {
                producto.setNombre(partialProducto.getNombre());
            }
            if (partialProducto.getTipoFlor() != null) {
                producto.setTipoFlor(partialProducto.getTipoFlor());
            }
            if (partialProducto.getPrecio() > 0) {
                producto.setPrecio(partialProducto.getPrecio());
            }
            if (partialProducto.getImagen() != null) {
                producto.setImagen(partialProducto.getImagen());
            }
            if (partialProducto.getFloricultor() != null) {
                producto.setFloricultor(partialProducto.getFloricultor());
            }
            productoRepository.save(producto);
            return ResponseEntity.ok(producto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar producto
    @DeleteMapping("/{idProducto}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long idProducto) {
        if (productoRepository.existsById(idProducto)) {
            productoRepository.deleteById(idProducto);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
