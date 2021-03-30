package br.com.senior.avaliacao.model.request;

import br.com.senior.avaliacao.model.response.ProdutoServicoResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ItemPedidoRequest {

    private UUID id;

    @JsonProperty("quantidade")
    private Integer qtd;

    @JsonProperty("valor")
    private BigDecimal valor;

    private BigDecimal desconto;

    @JsonProperty("valor_total")
    private BigDecimal valorTotal;

    @JsonProperty("produto_servico")
    private ProdutoServicoResponse produtoServico;

}
