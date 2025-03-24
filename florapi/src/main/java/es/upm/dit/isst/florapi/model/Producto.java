package es.upm.dit.isst.florapi.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import lombok.*;
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
    
    // Getters y Setters
}

