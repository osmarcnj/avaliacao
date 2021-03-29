package br.com.senior.avaliacao.model.request;

import br.com.senior.avaliacao.model.Pedido;
import br.com.senior.avaliacao.model.ProdutoServico;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ItemPedidoRequest {

    private String id;
    @JsonProperty("pedido_request")
    private PedidoRequest pedidoRequest;
    @JsonProperty("produto_servico_request")
    private ProdutoServicoRequest produtoServicoRequest;


}
