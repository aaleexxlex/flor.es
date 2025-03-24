package es.upm.dit.isst.florcliente.controller;

import es.upm.dit.isst.florcliente.model.*;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.Arrays;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import lombok.*;

@Getter @Setter

@Controller
@RequestMapping("/catalogo")
class FlorController {

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080"; // URL del backend

    public FlorController() {
        this.restTemplate = new RestTemplate();
    }

     // Mostrar catálogo de productos
     @GetMapping
     public String mostrarCatalogo(Model model) {
         // Realizamos una solicitud GET para obtener la lista de productos
         String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/productos").toUriString();
         List<Producto> productos = restTemplate.getForObject(url, List.class);
 
         model.addAttribute("productos", productos);
         return "catalogo"; // Vista para mostrar catálogo de productos
     }
    // Mostrar formulario para añadir un producto
    // Mostrar formulario para añadir un producto
    @GetMapping("/nuevo")
    public String mostrarFormularioProducto(Model model) {
        // Obtener la lista de floricultores desde el backend
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/floricultores").toUriString();
        List<Floricultor> floricultores = restTemplate.getForObject(url, List.class);

        model.addAttribute("floricultores", floricultores);
        model.addAttribute("producto", new Producto()); // Crear objeto Producto vacío para el formulario
        return "formularioProducto"; // Vista para crear un nuevo producto
    }
    // Procesar el formulario para crear un nuevo producto
    @PostMapping("/nuevo2")
    public String crearProducto(@ModelAttribute Producto producto) {
        // Enviar el nuevo producto al backend para almacenarlo
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/productos").toUriString();
        restTemplate.postForObject(url, producto, Producto.class);

        return "redirect:/catalogo"; // Redirigir al catálogo después de crear el producto
    }

        // Eliminar un producto
        @GetMapping("/eliminar/{id}")
        public String eliminarProducto(@PathVariable Long id) {
            // Realizamos una solicitud DELETE al backend para eliminar el producto con el id proporcionado
            String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/productos/" + id).toUriString();
            restTemplate.delete(url);
    
            return "redirect:/catalogo"; // Redirigir al catálogo después de eliminar el producto
        }
}