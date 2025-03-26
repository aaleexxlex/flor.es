package es.upm.dit.isst.florcliente.model;


import java.util.Date;
import java.util.List;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    private Long idProducto;
    private String nombre;
    private String tipoFlor;
    private double precio;
    
    
    private Floricultor floricultor;
    
    // Getters y Setters

    @Override
    public String toString() {
        return "Producto{idProducto=" + idProducto + ", nombre='" + nombre + "', tipoFlor='" + tipoFlor + "', precio=" + precio + ", floricultor=" + floricultor + "}";
    }
}

