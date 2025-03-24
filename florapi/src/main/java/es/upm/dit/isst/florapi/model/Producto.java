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
public class Producto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;
    private String nombre;
    private String tipoFlor;
    private double precio;
    
    @ManyToOne
    @JoinColumn(name = "email_floricultor")
    private Floricultor floricultor;
    
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<DetallePedido> detallesPedido;
    // Getters y Setters
}

