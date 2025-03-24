package es.upm.dit.isst.florcliente.model;


import java.util.Date;
import java.util.List;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedido {
  
    private Long idDetalle;
    private int cantidad;
    private double subtotal;

    private Pedido pedido;
    

    private Producto producto;
    
    // Getters y Setters
}
