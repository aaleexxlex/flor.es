package es.upm.dit.isst.florcliente.controller;

import es.upm.dit.isst.florcliente.model.Cliente;
import es.upm.dit.isst.florcliente.model.Floricultor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class RegistroController {

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080"; // URL del backend

    public RegistroController() {
        this.restTemplate = new RestTemplate();
    }

    /*@GetMapping("/logout")
    public String Logout(Model model) {
     
        return "redirect:/catalogo"; // Muestra la vista de registro
    }
*/
    @GetMapping("/login")
    public String mostrarFormularioLogin(Model model) {
     
        return "login"; // Muestra la vista de registro
    }

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("tipoUsuario", "cliente"); // Valor por defecto
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("floricultor", new Floricultor());
        return "registro"; // Muestra la vista de registro
    }

    @PostMapping("/registro")
    public String registrarUsuario(
            @RequestParam String tipoUsuario,
            @ModelAttribute Cliente cliente,
            @ModelAttribute Floricultor floricultor) {

        if ("cliente".equals(tipoUsuario)) {
            String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/clientes").toUriString();
            restTemplate.postForObject(url, cliente, Cliente.class);
        } else if ("floricultor".equals(tipoUsuario)) {
            String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/floricultores").toUriString();
            floricultor.setDisponibilidad(true); // Se establece como disponible por defecto
            restTemplate.postForObject(url, floricultor, Floricultor.class);
        }

        return "redirect:/home"; // Redirigir al catálogo después del registro
    }
}
