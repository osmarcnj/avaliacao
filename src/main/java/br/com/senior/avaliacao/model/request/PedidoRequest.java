package br.com.senior.avaliacao.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.OptBoolean;
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
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss",lenient = OptBoolean.FALSE)
    private Calendar dataPedido;

    private BigDecimal valor;

    @JsonProperty("valor_total")
    private BigDecimal valorTotal;

    private BigDecimal desconto;

    private Boolean ativo;

    @JsonProperty("item_pedido")
    private List<ItemPedidoRequest> itemPedidoRequestList;
}
