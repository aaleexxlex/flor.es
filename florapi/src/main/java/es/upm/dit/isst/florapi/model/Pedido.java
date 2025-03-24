package es.upm.dit.isst.florapi.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;
    private Date fecha;
    private String estado;
    private double total;
    private String destino;
    
    @ManyToOne
    @JoinColumn(name = "email_cliente")  // Relación con Cliente
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "email_floricultor")  // Relación con Floricultor
    private Floricultor floricultor;
    
    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Valoracion valoracion;
    
    // Getters y Setters
}
