package br.com.senior.avaliacao.model.request;

import br.com.senior.avaliacao.model.ItemPedido;
import com.fasterxml.jackson.annotation.JsonInclude;
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
    private Calendar dataPedido;
    private BigDecimal valorTotal;
    private BigDecimal desconto;
    private BigDecimal valorTotalComDesconto;
    private Boolean ativo;
    private List<ItemPedido> itemPedidoList;
}
