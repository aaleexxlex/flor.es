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
public String filtrarPorFlor(
    @PathVariable String tipoFlor,
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
                .filter(p -> p.getTipoFlor().equalsIgnoreCase(tipoFlor))
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
        model.addAttribute("tipoFlor", tipoFlor);
        model.addAttribute("tituloCategoria", obtenerNombrePlural(tipoFlor));
        model.addAttribute("color", color);
        model.addAttribute("origen", origen);
        model.addAttribute("precioMin", precioMin);
        model.addAttribute("precioMax", precioMax);
        model.addAttribute("disponible", disponible);

    } catch (Exception e) {
        model.addAttribute("productos", new ArrayList<>());
    }

    return "productosPorCategoria";
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
    public String filtrarRamosConFiltros(
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
            List<Producto> filtrados = productos.stream()
                .filter(p -> p.isEsRamo()) // Solo ramos
                .filter(p -> color == null || color.isEmpty() || p.getColor().equalsIgnoreCase(color))
                .filter(p -> origen == null || origen.isEmpty() || p.getFloricultor().getUbicacion().equalsIgnoreCase(origen))
                .filter(p -> precioMin == null || p.getPrecio() >= precioMin)
                .filter(p -> precioMax == null || p.getPrecio() <= precioMax)
                .filter(p -> disponible == null || !disponible || p.getCantidad() > 0)
                .toList();
    
            model.addAttribute("productos", filtrados);
            model.addAttribute("tituloCategoria", "Ramos");
            model.addAttribute("tipoFlor", "ramos");
            model.addAttribute("color", color);
            model.addAttribute("origen", origen);
            model.addAttribute("precioMin", precioMin);
            model.addAttribute("precioMax", precioMax);
            model.addAttribute("disponible", disponible);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("productos", new ArrayList<>());
            model.addAttribute("tituloCategoria", "Ramos");
        }
    
        return "productosPorCategoria";
    }
    

}
