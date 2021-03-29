package br.com.senior.avaliacao.model.response;

import br.com.senior.avaliacao.model.ItemPedido;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PedidoResponse {

    private UUID id;
    private Calendar dataPedido;
    private BigDecimal valorTotal;
    private BigDecimal desconto;
    private BigDecimal valorTotalComDesconto;
    private Boolean ativo;
    private List<ItemPedido> itemPedidoList;
}
