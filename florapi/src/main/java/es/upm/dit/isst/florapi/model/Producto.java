package es.upm.dit.isst.florapi.model;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"lineasPedido", "floricultor"}) // evitamos ciclos infinitos
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idProducto;

    private String nombre;
    private String tipoFlor;
    private double precio;

    private String imagen;

    @ManyToOne
    @JoinColumn(name = "email_floricultor")
    private Floricultor floricultor;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<LineaPedido> lineasPedido;
}
