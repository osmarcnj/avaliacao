package br.com.senior.avaliacao.Util;

import br.com.senior.avaliacao.enums.TipoEnum;
import br.com.senior.avaliacao.model.ItemPedido;
import br.com.senior.avaliacao.model.Pedido;
import br.com.senior.avaliacao.model.ProdutoServico;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class UtilSetting {

    public Pedido calcularTotalPedido(final Pedido pedido) {
        if(pedido.getAtivo()){
            pedido.setValor(BigDecimal.ZERO);
            pedido.setValorTotal(BigDecimal.ZERO);
             BigDecimal vlr = BigDecimal.ZERO;
             pedido.getItemPedidoList()
                .forEach(itemPedido -> {
                    pedido.setValor(pedido.getValor().add(itemPedido.getValor()));
                    if(pedido.getDesconto().doubleValue() > 0 &&
                            itemPedido.getProdutoServico().getTipoDado().equals(TipoEnum.PRODUTO)) {
                        pedido.setValorTotal(pedido.getValorTotal().add(itemPedido.getValorTotal()));

                    }
                });
        vlr = (pedido.getValorTotal().multiply(pedido.getDesconto().divide(new BigDecimal(100))));
        pedido.setValorTotal(pedido.getValor().subtract(vlr));
        }
        return  pedido;
    }

    public ItemPedido criarItem(final Pedido pedido, final ProdutoServico ps,
                                 final int qtd) {
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setQtd(qtd);
        itemPedido.setValor(BigDecimal.valueOf(ps.getValor().doubleValue() * itemPedido.getQtd()));
        itemPedido.setValorTotal(itemPedido.getValor());
        itemPedido.setPedido(pedido);
        itemPedido.setProdutoServico(ps);

        return itemPedido;
    }


}
