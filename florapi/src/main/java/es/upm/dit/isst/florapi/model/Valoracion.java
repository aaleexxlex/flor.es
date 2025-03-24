package es.upm.dit.isst.florapi.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import lombok.*;
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Valoracion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idValoracion;
    private String tipoValoracion;
    private int calificacion;
    private String comentario;
    private Date fecha;
    
    @OneToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;
    
    // Getters y Setters
}