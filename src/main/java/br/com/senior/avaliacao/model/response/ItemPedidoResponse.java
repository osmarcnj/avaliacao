package br.com.senior.avaliacao.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ItemPedidoResponse {

    private String id;

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
