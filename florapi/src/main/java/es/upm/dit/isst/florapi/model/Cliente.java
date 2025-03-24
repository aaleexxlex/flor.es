package es.upm.dit.isst.florapi.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @JsonIgnore 
    private List<Pedido> pedidos;
    
    // Getters y Setters
}
