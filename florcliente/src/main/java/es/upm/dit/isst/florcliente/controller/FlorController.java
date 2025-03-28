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
import org.springframework.http.ResponseEntity;

import java.util.List;

import lombok.*;

@Getter @Setter

@Controller
@RequestMapping("/catalogo")
class FlorController {

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080"; // URL del backend
    private boolean sesioniniciada;

    public FlorController() {
        this.restTemplate = new RestTemplate();
    }

    // Método para iniciar sesión con email
    @PostMapping("/iniciar-sesion")
    public String iniciarSesion(@RequestParam("email") String email, Model model) {
        try {
            // Verificar si es cliente
            String urlCliente = UriComponentsBuilder.fromHttpUrl(baseUrl + "/clientes/" + email).toUriString();
            restTemplate.getForEntity(urlCliente, Cliente.class);
            return "redirect:/catalogo/catalogoCliente/" + email;
        } catch (Exception e) {
            // Cliente no encontrado, intentar con floricultor
            try {
                String urlFloricultor = UriComponentsBuilder.fromHttpUrl(baseUrl + "/floricultores/" + email).toUriString();
                restTemplate.getForEntity(urlFloricultor, Floricultor.class);
                return "redirect:/catalogo/catalogoFloricultor/" + email;
            } catch (Exception ex) {
                // Ni cliente ni floricultor encontrados
                return "redirect:/catalogo";
            }
        }
    }

    // Mostrar catálogo de productos
    @GetMapping
    public String mostrarCatalogo(Model model) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/productos").toUriString();
            List<Producto> productos = restTemplate.getForObject(url, List.class);
            model.addAttribute("productos", productos);
        } catch (Exception e) {
            model.addAttribute("productos", new ArrayList<Producto>());
            // Puedes agregar un mensaje de error o algo similar
        }
        return "catalogo"; // Vista para mostrar catálogo de productos
    }

    @GetMapping("/catalogoCliente/{email}")
    public String mostrarCatalogoCliente(@PathVariable String email, Model model) {
        try {
            // Obtener los datos del cliente
            String urlCliente = UriComponentsBuilder.fromHttpUrl(baseUrl + "/clientes/" + email).toUriString();
            Cliente cliente = restTemplate.getForObject(urlCliente, Cliente.class);
            model.addAttribute("cliente", cliente);
        } catch (Exception e) {
            model.addAttribute("cliente", null);
        }

        try {
            // Obtener los productos disponibles para el cliente
            String urlProductos = UriComponentsBuilder.fromHttpUrl(baseUrl + "/productos").toUriString();
            List<Producto> productos = restTemplate.getForObject(urlProductos, List.class);
            model.addAttribute("productos", productos);
        } catch (Exception e) {
            model.addAttribute("productos", new ArrayList<Producto>());
        }

        return "catalogoCliente"; // Vista para mostrar catálogo del cliente
    }

    @GetMapping("/catalogoFloricultor/{email}")
    public String mostrarCatalogoFloricultor(@PathVariable String email, Model model) {
        try {
            // Obtener los datos del floricultor
            String urlFloricultor = UriComponentsBuilder.fromHttpUrl(baseUrl + "/floricultores/" + email).toUriString();
            Floricultor floricultor = restTemplate.getForObject(urlFloricultor, Floricultor.class);
            model.addAttribute("floricultor", floricultor);
        } catch (Exception e) {
            model.addAttribute("floricultor", null);
        }

        try {
            // Obtener los productos disponibles para el floricultor
            String urlProductos = UriComponentsBuilder.fromHttpUrl(baseUrl + "/productos").toUriString();
            List<Producto> productos = restTemplate.getForObject(urlProductos, List.class);
            model.addAttribute("productos", productos);
        } catch (Exception e) {
            model.addAttribute("productos", new ArrayList<Producto>());
        }

        return "catalogoFloricultor"; // Vista para mostrar catálogo del floricultor
    }

    @GetMapping("/inventario/{email}")
    public String mostrarInventario(@PathVariable String email, Model model) {
        try {
            // Obtener los datos del floricultor
            String urlFloricultor = UriComponentsBuilder.fromHttpUrl(baseUrl + "/floricultores/" + email).toUriString();
            Floricultor floricultor = restTemplate.getForObject(urlFloricultor, Floricultor.class);
            model.addAttribute("floricultor", floricultor);
        } catch (Exception e) {
            model.addAttribute("floricultor", null);
        }

        try {
            // Obtener los productos que pertenecen al floricultor
            String urlProductos = UriComponentsBuilder.fromHttpUrl(baseUrl + "/productos/floricultor/" + email).toUriString();
            List<Producto> productos = restTemplate.getForObject(urlProductos, List.class);
            model.addAttribute("productos", productos);
        } catch (Exception e) {
            model.addAttribute("productos", new ArrayList<Producto>());
        }

        model.addAttribute("email", email);

        return "inventario";
    }

    @GetMapping("/producto/{id}")
    public String mostrarDetalleProducto(@PathVariable Long id, Model model) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/productos/" + id).toUriString();
            Producto producto = restTemplate.getForObject(url, Producto.class);
            model.addAttribute("producto", producto);
        } catch (Exception e) {
            model.addAttribute("producto", null);
        }
        return "detalleProducto";
    }

    @GetMapping("/productos/nuevo/{email}")
    public String mostrarFormularioProducto(@PathVariable String email, Model model) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/floricultores/" + email).toUriString();
            Floricultor floricultor = restTemplate.getForObject(url, Floricultor.class);
            model.addAttribute("floricultor", floricultor);
        } catch (Exception e) {
            model.addAttribute("floricultor", null);
        }

        model.addAttribute("producto", new Producto());
        return "formularioProducto";
    }

    @PostMapping("/productos/crear/{email}")
    public String crearProducto(@PathVariable String email, @ModelAttribute Producto producto) {
        try {
            String urlFloricultor = UriComponentsBuilder
                    .fromHttpUrl(baseUrl + "/floricultores/" + email)
                    .toUriString();
            Floricultor floricultor = restTemplate.getForObject(urlFloricultor, Floricultor.class);
    
            producto.setFloricultor(floricultor);
            producto.setImagen(asignarImagenPorTipo(producto.getTipoFlor()));
    
            String urlProducto = UriComponentsBuilder
                    .fromHttpUrl(baseUrl + "/productos")
                    .toUriString();
            restTemplate.postForObject(urlProducto, producto, Producto.class);
        } catch (Exception e) {
            System.err.println("Error al crear el producto: " + e.getMessage());
            e.printStackTrace();
        }
    
        return "redirect:/catalogo/catalogoFloricultor/" + email;
    }
    
    
    private String asignarImagenPorTipo(String tipo) {
        tipo = tipo.toLowerCase();
        return switch (tipo) {
            case "rosa" -> "/images/rosas.jpg";
            case "tulipán" -> "/images/tulipanes.jpg";
            case "girasol" -> "/images/girasoles.jpeg";
            case "lirio" -> "/images/lirios.jpg";
            case "margarita" -> "/images/margaritas.jpg";
            case "peonía", "peonia" -> "/images/peonias.jpg";
            default -> "/images/floresvarias.webp";
        };
    }

    @GetMapping("/pedidos/nuevo")
    public String mostrarFormularioPedido(Model model) {
        try {
            String urlProductos = UriComponentsBuilder.fromHttpUrl(baseUrl + "/productos").toUriString();
            List<Producto> productos = restTemplate.getForObject(urlProductos, List.class);
            model.addAttribute("productos", productos);
        } catch (Exception e) {
            model.addAttribute("productos", new ArrayList<Producto>());
        }

        try {
            String urlFloricultores = UriComponentsBuilder.fromHttpUrl(baseUrl + "/floricultores").toUriString();
            List<Floricultor> floricultores = restTemplate.getForObject(urlFloricultores, List.class);
            model.addAttribute("floricultores", floricultores);
        } catch (Exception e) {
            model.addAttribute("floricultores", new ArrayList<Floricultor>());
        }

        model.addAttribute("pedido", new Pedido());
        model.addAttribute("detallePedido", new DetallePedido());

        return "formularioPedido";
    }

    @PostMapping("/pedidos/crear")
    public String crearPedido(@ModelAttribute Pedido pedido, @ModelAttribute DetallePedido detallePedido) {
        try {
            String urlPedido = UriComponentsBuilder.fromHttpUrl(baseUrl + "/pedidos").toUriString();
            Pedido nuevoPedido = restTemplate.postForObject(urlPedido, pedido, Pedido.class);

            // Asociar el detalle al pedido
            detallePedido.setPedido(nuevoPedido);
            String urlDetalle = UriComponentsBuilder.fromHttpUrl(baseUrl + "/detallesPedido").toUriString();
            restTemplate.postForObject(urlDetalle, detallePedido, DetallePedido.class);
        } catch (Exception e) {
            // Manejar el error si el pedido o detalle no se pueden crear
        }

        return "redirect:/catalogo";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id, @RequestParam String email) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/productos/" + id).toUriString();
            restTemplate.delete(url);
        } catch (Exception e) {
            // Manejar el error si no se puede eliminar el producto
        }

        return "redirect:/catalogo/inventario/" + email;
    }
}