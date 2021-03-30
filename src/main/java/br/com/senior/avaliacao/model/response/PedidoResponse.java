package br.com.senior.avaliacao.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PedidoResponse {

    private String id;

    @JsonProperty("data_pedido")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss",lenient = OptBoolean.FALSE)
    private Calendar  dataPedido;

    private BigDecimal valor;

    @JsonProperty("valor_desconto")
    private BigDecimal desconto;

    @JsonProperty("valor_total")
    private BigDecimal valorTotal;

    private Boolean ativo;

    @JsonProperty("item_pedido")
    private List<ItemPedidoResponse> itemPedidoResponseList;
}
