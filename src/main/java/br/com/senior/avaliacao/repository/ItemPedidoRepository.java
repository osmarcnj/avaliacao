package br.com.senior.avaliacao.repository;

import br.com.senior.avaliacao.model.ItemPedido;
import br.com.senior.avaliacao.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.UUID;

public interface ItemPedidoRepository  extends JpaRepository<ItemPedido, UUID> {

}