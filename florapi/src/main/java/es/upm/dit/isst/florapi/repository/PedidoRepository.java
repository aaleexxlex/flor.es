package es.upm.dit.isst.florapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.upm.dit.isst.florapi.model.*;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteEmail(String email);
    List<Pedido> findByFloricultorEmail(String email);

}
