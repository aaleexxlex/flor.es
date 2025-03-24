package es.upm.dit.isst.florapi.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    @Id 
    private String email;  // email como identificador único
    private String nombre;
    private String direccion;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;
    
    // Getters y Setters
}
