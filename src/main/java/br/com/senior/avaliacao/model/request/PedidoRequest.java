package br.com.senior.avaliacao.model.request;

import br.com.senior.avaliacao.model.ItemPedido;
import br.com.senior.avaliacao.model.response.ItemPedidoResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PedidoRequest {

    private String id;
    @JsonProperty("data_pedido")
    private Calendar dataPedido;
    @JsonProperty("valor_total")
    private BigDecimal valorTotal;
    private BigDecimal desconto;
    @JsonProperty("valor_total_com_desconto")
    private BigDecimal valorTotalComDesconto;
    private Boolean ativo;
    @JsonProperty("item_pedido_request_list")
    private List<ItemPedidoRequest> itemPedidoRequestList;
}
