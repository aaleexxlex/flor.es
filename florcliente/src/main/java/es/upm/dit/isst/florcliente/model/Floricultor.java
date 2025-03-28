package es.upm.dit.isst.florcliente.model;

import lombok.*;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Floricultor {
    private String nombre;
    private String email;
    private String ubicacion;
    private boolean disponibilidad;

    private List<Pedido> pedidos;
    private List<Producto> productos;
}
