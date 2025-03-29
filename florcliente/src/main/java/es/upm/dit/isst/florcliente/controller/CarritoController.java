package es.upm.dit.isst.florcliente.controller;

import es.upm.dit.isst.florcliente.model.LineaPedido;
import es.upm.dit.isst.florcliente.model.Producto;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/carrito")
@SessionAttributes({"usuario", "rol"})
public class CarritoController {

    @ModelAttribute("usuario")
    public Object usuario() {
        return null;
    }

    @ModelAttribute("rol")
    public String rol() {
        return "";
    }

    @PostMapping("/agregar")
    public String agregarProducto(@ModelAttribute Producto producto, @RequestParam int cantidad, HttpSession session) {
        List<LineaPedido> carrito = (List<LineaPedido>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
        }

        Optional<LineaPedido> existente = carrito.stream()
                .filter(lp -> lp.getProducto().getIdProducto().equals(producto.getIdProducto()))
                .findFirst();

        if (existente.isPresent()) {
            LineaPedido lp = existente.get();
            lp.setCantidad(lp.getCantidad() + cantidad);
        } else {
            LineaPedido nueva = new LineaPedido();
            nueva.setProducto(producto);
            nueva.setCantidad(cantidad);
            nueva.setPrecioUnitario(producto.getPrecio());
            carrito.add(nueva);
        }

        session.setAttribute("carrito", carrito);
        return "redirect:/carrito/ver";
    }

    @GetMapping("/ver")
    public String verCarrito(Model model, HttpSession session, @ModelAttribute("usuario") Object usuario) {
        List<LineaPedido> carrito = (List<LineaPedido>) session.getAttribute("carrito");
        if (carrito == null) carrito = new ArrayList<>();
        model.addAttribute("carrito", carrito);
        model.addAttribute("usuario", usuario);

        double total = carrito.stream().mapToDouble(LineaPedido::getSubtotal).sum();
        model.addAttribute("total", total);

        return "carrito";
    }

    @PostMapping("/vaciar")
    public String vaciarCarrito(HttpSession session) {
        session.removeAttribute("carrito");
        return "redirect:/carrito/ver";
    }

    @ModelAttribute("totalCarrito")
    public double getTotalCarrito(HttpSession session) {
        List<LineaPedido> carrito = (List<LineaPedido>) session.getAttribute("carrito");
        if (carrito == null) return 0.0;
        return carrito.stream().mapToDouble(lp -> lp.getCantidad() * lp.getPrecioUnitario()).sum();
    }

    @PostMapping("/sumar")
    public String sumarCantidad(@RequestParam Long idProducto, HttpSession session) {
        List<LineaPedido> carrito = (List<LineaPedido>) session.getAttribute("carrito");
        if (carrito != null) {
            for (LineaPedido linea : carrito) {
                if (linea.getProducto().getIdProducto().equals(idProducto)) {
                    linea.setCantidad(linea.getCantidad() + 1);
                    break;
                }
            }
            session.setAttribute("carrito", carrito);
        }
        return "redirect:/carrito/ver";
    }

    @PostMapping("/restar")
    public String restarCantidad(@RequestParam Long idProducto, HttpSession session) {
        List<LineaPedido> carrito = (List<LineaPedido>) session.getAttribute("carrito");
        if (carrito != null) {
            Iterator<LineaPedido> iter = carrito.iterator();
            while (iter.hasNext()) {
                LineaPedido linea = iter.next();
                if (linea.getProducto().getIdProducto().equals(idProducto)) {
                    if (linea.getCantidad() > 1) {
                        linea.setCantidad(linea.getCantidad() - 1);
                    } else {
                        iter.remove();
                    }
                    break;
                }
            }
            session.setAttribute("carrito", carrito);
        }
        return "redirect:/carrito/ver";
    }
}
