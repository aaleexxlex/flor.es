package es.upm.dit.isst.florcliente.controller;

import es.upm.dit.isst.florcliente.model.*;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.Arrays;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpSession;
import java.util.List;

import lombok.*;

@Getter @Setter
@Controller
@SessionAttributes({"usuario", "rol"})
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
                Floricultor floricultor = restTemplate.getForObject(baseUrl + "/floricultores/" + email, Floricultor.class);
                model.addAttribute("usuario", floricultor);
                model.addAttribute("rol", "floricultor");
                return "redirect:/home";
            } catch (Exception ex) {
                return "redirect:/login?error";
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
                pedidos = restTemplate.getForObject(baseUrl + "/pedidos/cliente/" + ((Cliente) usuario).getEmail(), List.class);
            } catch (Exception ignored) {}
            model.addAttribute("pedidos", pedidos);
        } else if ("floricultor".equals(rol)) {
            List<Producto> productos = new ArrayList<>();
            try {
                productos = restTemplate.getForObject(baseUrl + "/productos/floricultor/" + ((Floricultor) usuario).getEmail(), List.class);
            } catch (Exception ignored) {}
            model.addAttribute("productos", productos);
        }
        return "cuenta";
    }
    @GetMapping("/tienda")
    public String mostrarTienda(Model model) {
    try {
        List<Producto> productos = restTemplate.getForObject(baseUrl + "/productos", List.class);
        model.addAttribute("productos", productos);
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
    public String cerrarSesion(SessionStatus status) {
        status.setComplete();
        return "redirect:/home";
    }

    @GetMapping("/producto/{id}")
    public String mostrarDetalleProducto(@PathVariable Long id, Model model) {
        try {
            Producto producto = restTemplate.getForObject(baseUrl + "/productos/" + id, Producto.class);
            model.addAttribute("producto", producto);
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

    @PostMapping("/productos/crear")
    public String crearProducto(@ModelAttribute Producto producto, @ModelAttribute("usuario") Floricultor floricultor) {
        try {
            producto.setFloricultor(floricultor);
            restTemplate.postForObject(baseUrl + "/productos", producto, Producto.class);
        } catch (Exception e) {
            // error al crear
        }
        return "redirect:/cuenta";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        try {
            restTemplate.delete(baseUrl + "/productos/" + id);
        } catch (Exception e) {
            // error al eliminar
        }
        return "redirect:/cuenta";
    }
}
