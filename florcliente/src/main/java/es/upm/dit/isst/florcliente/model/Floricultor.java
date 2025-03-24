package es.upm.dit.isst.florcliente.model;


import java.util.Date;
import java.util.List;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Floricultor {
  
 
    private String nombre;
    private String email;
    private String ubicacion;
    private boolean disponibilidad;
    
    // Getters y Setters
}