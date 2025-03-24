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
public class Floricultor {
    @Id 
    private String email;  // email como identificador único
    private String nombre;
    private String ubicacion;
    private boolean disponibilidad;

    @OneToMany(mappedBy = "floricultor")
    @JsonIgnore
    private List<Pedido> pedidos;

    @OneToMany(mappedBy = "floricultor")
    @JsonIgnore 
    private List<Producto> productos;
    
    // Getters y Setters
}