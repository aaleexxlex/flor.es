package es.upm.dit.isst.florcliente.controller;

import es.upm.dit.isst.florcliente.model.Producto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/home")
public class CategoriaController {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String baseUrl = "http://localhost:8080";

    @GetMapping("/{tipoFlor}")
    public String filtrarPorFlor(@PathVariable String tipoFlor, Model model) {
        try {
            ResponseEntity<List<Producto>> response = restTemplate.exchange(
                    baseUrl + "/productos", // endpoint que devuelve todos los productos
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Producto>>() {
                    });

            List<Producto> todos = response.getBody();
            if (todos != null) {
                // Normalizamos a minúsculas para evitar problemas
                List<Producto> filtrados = todos.stream()
                        .filter(p -> p.getTipoFlor().equalsIgnoreCase(tipoFlor))
                        .toList();
                model.addAttribute("productos", filtrados);
            } else {
                model.addAttribute("productos", new ArrayList<>());
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("productos", new ArrayList<>());
        }

        model.addAttribute("tipoFlor", tipoFlor);
        model.addAttribute("tituloCategoria", obtenerNombrePlural(tipoFlor));

        return "productosPorCategoria"; // Cambia a la vista que desees
    }

    private String obtenerNombrePlural(String tipoFlor) {
        return switch (tipoFlor.toLowerCase()) {
            case "rosa" -> "Rosas";
            case "tulipán", "tulipan" -> "Tulipanes";
            case "girasol" -> "Girasoles";
            case "lirio" -> "Lirios";
            case "margarita" -> "Margaritas";
            case "peonía", "peonia" -> "Peonías";
            default -> tipoFlor.substring(0, 1).toUpperCase() + tipoFlor.substring(1) + "s";
        };
    }

    @GetMapping("/ramos")
    public String mostrarRamos(Model model) {
        try {
            ResponseEntity<List<Producto>> response = restTemplate.exchange(
                    baseUrl + "/productos",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Producto>>() {
                    });

            List<Producto> productos = response.getBody();
            List<Producto> ramos = productos.stream()
                    .filter(Producto::isEsRamo)
                    .toList();

            model.addAttribute("productos", ramos);
            model.addAttribute("tituloCategoria", "Ramos");

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("productos", new ArrayList<>());
            model.addAttribute("tituloCategoria", "Ramos");
        }

        return "productosPorCategoria";
    }

}
