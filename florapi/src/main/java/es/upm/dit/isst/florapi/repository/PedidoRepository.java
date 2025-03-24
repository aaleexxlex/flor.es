package es.upm.dit.isst.florapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import es.upm.dit.isst.florapi.model.*;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {}
