package es.upm.dit.isst.florcliente.controller;

import es.upm.dit.isst.florcliente.model.*;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.Arrays;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpSession;
import java.util.List;

import lombok.*;

@Getter
@Setter
@Controller
@SessionAttributes({ "usuario", "rol" })
public class FlorController {

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080";

    public FlorController() {
        this.restTemplate = new RestTemplate();
    }

    @ModelAttribute("usuario")
    public Object usuario() {
        return null;
    }

    @ModelAttribute("rol")
    public String rol() {
        return "";
    }

    @PostMapping("/iniciar-sesion")
    public String iniciarSesion(@RequestParam("email") String email, Model model) {
        try {
            Cliente cliente = restTemplate.getForObject(baseUrl + "/clientes/" + email, Cliente.class);
            model.addAttribute("usuario", cliente);
            model.addAttribute("rol", "cliente");
            return "redirect:/home";
        } catch (Exception e) {
            try {
                Floricultor floricultor = restTemplate.getForObject(baseUrl + "/floricultores/" + email,
                        Floricultor.class);
                model.addAttribute("usuario", floricultor);
                model.addAttribute("rol", "floricultor");
                return "redirect:/home";
            } catch (Exception ex) {
                model.addAttribute("mensajeError", "No existe un usuario registrado con ese email.");
                return "login";
            }
        }
    }

    @GetMapping("/home")
    public String mostrarHome(Model model) {
        try {
            List<Producto> productos = restTemplate.getForObject(baseUrl + "/productos", List.class);
            model.addAttribute("productos", productos);
        } catch (Exception e) {
            model.addAttribute("productos", new ArrayList<>());
        }
        return "home";
    }

    @GetMapping("/cuenta")
    public String verCuenta(@ModelAttribute("rol") String rol, Model model, @ModelAttribute("usuario") Object usuario) {
        if ("cliente".equals(rol)) {
            List<Pedido> pedidos = new ArrayList<>();
            try {
                ResponseEntity<List<Pedido>> response = restTemplate.exchange(
                        baseUrl + "/pedidos/cliente/" + ((Cliente) usuario).getEmail(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Pedido>>() {
                        });
                pedidos = response.getBody();
            } catch (Exception e) {
                e.printStackTrace();
            }
            model.addAttribute("pedidos", pedidos);
        } else if ("floricultor".equals(rol)) {
            List<Producto> productos = new ArrayList<>();
            List<Pedido> pedidosRecibidos = new ArrayList<>();
            String email = ((Floricultor) usuario).getEmail();

            try {
                ResponseEntity<List<Producto>> response = restTemplate.exchange(
                        baseUrl + "/productos/floricultor/" + email,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Producto>>() {
                        });
                productos = response.getBody();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ResponseEntity<List<Pedido>> response = restTemplate.exchange(
                        baseUrl + "/pedidos/floricultor/" + email,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Pedido>>() {
                        });
                pedidosRecibidos = response.getBody();
            } catch (

            Exception e) {
                e.printStackTrace();
            }

            model.addAttribute("productos", productos);
            model.addAttribute("pedidosRecibidos", pedidosRecibidos);
        }

        return "cuenta";
    }

    @GetMapping("/tienda")
    public String mostrarTienda(
        @RequestParam(required = false) String color,
        @RequestParam(required = false) String origen,
        @RequestParam(required = false) Double precioMin,
        @RequestParam(required = false) Double precioMax,
        @RequestParam(required = false) Boolean disponible,
        Model model
    ) {
        try {
            ResponseEntity<List<Producto>> response = restTemplate.exchange(
                baseUrl + "/productos",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Producto>>() {}
            );
    
            List<Producto> productos = response.getBody();
    
            if (productos != null) {
                productos = productos.stream()
                    .filter(p -> color == null || color.isEmpty() || p.getColor().equalsIgnoreCase(color))
                    .filter(p -> origen == null || origen.isEmpty() || 
                                 (origen.equalsIgnoreCase("Madrid") && p.getFloricultor().getUbicacion().equalsIgnoreCase("Madrid")) ||
                                 (origen.equalsIgnoreCase("Barcelona") && p.getFloricultor().getUbicacion().equalsIgnoreCase("Barcelona")))
                    .filter(p -> (precioMin == null || p.getPrecio() >= precioMin) &&
                                 (precioMax == null || p.getPrecio() <= precioMax))
                    .filter(p -> disponible == null || !disponible || p.getCantidad() > 0)
                    .toList();
            }
    
            model.addAttribute("productos", productos);
            model.addAttribute("color", color);
            model.addAttribute("origen", origen);
            model.addAttribute("precioMin", precioMin);
            model.addAttribute("precioMax", precioMax);
            model.addAttribute("disponible", disponible);
    
        } catch (Exception e) {
            model.addAttribute("productos", new ArrayList<>());
        }
    
        return "tienda";
    }
    

    @GetMapping("/")
    public String redirigirRaiz() {
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String cerrarSesion(SessionStatus status, HttpSession session) {
        status.setComplete();
        session.invalidate();
        return "redirect:/home";
    }

    @GetMapping("/producto/{id}")
    public String mostrarDetalleProducto(@PathVariable Long id, Model model, HttpSession session) {
        try {
            Producto producto = restTemplate.getForObject(baseUrl + "/productos/" + id, Producto.class);
            model.addAttribute("producto", producto);

            // Obtener número de valoraciones
            Long numValoraciones = restTemplate.getForObject(
                    baseUrl + "/valoraciones/producto/" + id + "/count",
                    Long.class);
            model.addAttribute("numValoraciones", numValoraciones);
            Double mediaValoraciones = restTemplate.getForObject(
                    baseUrl + "/valoraciones/producto/" + id + "/media",
                    Double.class);
            model.addAttribute("mediaValoraciones", mediaValoraciones);

            String errorCarrito = (String) session.getAttribute("errorCarrito");
            if (errorCarrito != null) {
                model.addAttribute("errorCarrito", errorCarrito);
                session.removeAttribute("errorCarrito");
            }

            String mensajeExito = (String) session.getAttribute("mensajeExito");
            if (mensajeExito != null) {
                model.addAttribute("mensajeExito", mensajeExito);
                session.removeAttribute("mensajeExito");
            }

        } catch (Exception e) {
            model.addAttribute("producto", null);
        }
        return "detalleProducto";
    }

    @GetMapping("/productos/nuevo")
    public String mostrarFormularioProducto(@ModelAttribute("usuario") Floricultor floricultor, Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("floricultor", floricultor);
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
            producto.setImagen(asignarImagenPorTipo(producto.getTipoFlor(), producto.isEsRamo()));

            String urlProducto = UriComponentsBuilder
                    .fromHttpUrl(baseUrl + "/productos")
                    .toUriString();
            restTemplate.postForObject(urlProducto, producto, Producto.class);
        } catch (Exception e) {
            System.err.println("Error al crear el producto: " + e.getMessage());
            e.printStackTrace();
        }

        return "redirect:/cuenta";
    }

    private String asignarImagenPorTipo(String tipo, boolean esRamo) {
        tipo = tipo.toLowerCase();

        if (esRamo) {
            return switch (tipo) {
                case "rosa" -> "/images/ramoRosas.jpg";
                case "tulipán", "tulipan" -> "/images/ramoTulipanes.jpg";
                case "girasol" -> "/images/ramoGirasol.jpeg";
                case "lirio" -> "/images/ramoLirios.jpg";
                case "margarita" -> "/images/ramoMargaritas.jpg";
                case "peonía", "peonia" -> "/images/ramoPeonias.jpg";
                default -> "/images/floresvarias.webp";
            };
        } else {
            return switch (tipo) {
                case "rosa" -> "/images/rosa.png";
                case "tulipán", "tulipan" -> "/images/tulipan.png";
                case "girasol" -> "/images/girasol.png";
                case "lirio" -> "/images/lirio.png";
                case "margarita" -> "/images/margarita.png";
                case "peonía", "peonia" -> "/images/peonia.png";
                default -> "/images/floresvarias.webp";
            };
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        try {
            restTemplate.delete(baseUrl + "/productos/" + id);
        } catch (Exception e) {
        }
        return "redirect:/cuenta";
    }

    // Editar producto del inventario
    @GetMapping("/producto/editar/{id}")
    public String mostrarFormularioEditarProducto(@PathVariable Long id, Model model) {
        try {
            Producto producto = restTemplate.getForObject(baseUrl + "/productos/" + id, Producto.class);
            model.addAttribute("producto", producto);
        } catch (Exception e) {
            model.addAttribute("producto", null);
        }
        return "formularioProducto";
    }

    // Ver detalle del pedido
    @GetMapping("/pedido/{id}")
    public String verDetallePedido(@PathVariable Long id, Model model,
            @ModelAttribute("rol") String rol) {
        try {
            ResponseEntity<Pedido> response = restTemplate.getForEntity(baseUrl + "/pedidos/" + id, Pedido.class);
            Pedido pedido = response.getBody();
            model.addAttribute("pedido", pedido);
            model.addAttribute("rol", rol);
            return "detallePedido";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/cuenta";
        }
    }

    @PostMapping("/producto/editar")
    public String actualizarProducto(@ModelAttribute Producto producto) {
        try {
            producto.setImagen(asignarImagenPorTipo(producto.getTipoFlor(), producto.isEsRamo()));
            restTemplate.put(baseUrl + "/productos/" + producto.getIdProducto(), producto);
        } catch (Exception e) {
            System.err.println("Error al actualizar el producto: " + e.getMessage());
        }
        return "redirect:/cuenta";
    }

    @GetMapping("/pedido/confirmado")
    public String mostrarConfirmacion() {
        return "pedidoConfirmado";
    }
    @GetMapping("/floricultores")
public String verFloricultores(Model model) {
    try {
        // Obtienes todos los floricultores
        ResponseEntity<List<Floricultor>> response = restTemplate.exchange(
            baseUrl + "/floricultores",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Floricultor>>() {}
        );

        List<Floricultor> floricultores = response.getBody();

        // Para cada floricultor, traes sus productos
        for (Floricultor flor : floricultores) {
            ResponseEntity<List<Producto>> productosResponse = restTemplate.exchange(
                baseUrl + "/productos/floricultor/" + flor.getEmail(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Producto>>() {}
            );
            flor.setProductos(productosResponse.getBody());
        }

        model.addAttribute("floricultores", floricultores);
    } catch (Exception e) {
        model.addAttribute("floricultores", new ArrayList<>());
    }

    return "floricultores"; // nombre del HTML
}

}
