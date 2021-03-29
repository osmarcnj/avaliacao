package br.com.senior.avaliacao.model.response;

import br.com.senior.avaliacao.model.Pedido;
import br.com.senior.avaliacao.model.ProdutoServico;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ItemPedidoResponse {


    private UUID id;
    private PedidoResponse pedidoResponse;
    private ProdutoServicoResponse produtoServicoResponse;
}
