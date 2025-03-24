package es.upm.dit.isst.florcliente.model;


import java.util.Date;
import java.util.List;
import lombok.*;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
   
    private Long idPedido;
    private Date fecha;
    private String estado;
    private double total;
    private String destino;
    
  
    private Cliente cliente;
    

    private Floricultor floricultor;
    

    private Valoracion valoracion;
    
    // Getters y Setters
}
