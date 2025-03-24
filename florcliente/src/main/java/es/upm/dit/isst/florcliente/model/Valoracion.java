package es.upm.dit.isst.florcliente.model;


import java.util.Date;
import java.util.List;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Valoracion {
  
    private Long idValoracion;
    private String tipoValoracion;
    private int calificacion;
    private String comentario;
    private Date fecha;
    

    private Pedido pedido;
    
    // Getters y Setters
}