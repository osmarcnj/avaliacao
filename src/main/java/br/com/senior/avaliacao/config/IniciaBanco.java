package br.com.senior.avaliacao.config;

import br.com.senior.avaliacao.enums.TipoEnum;
import br.com.senior.avaliacao.model.ItemPedido;
import br.com.senior.avaliacao.model.Pedido;
import br.com.senior.avaliacao.model.ProdutoServico;
import br.com.senior.avaliacao.repository.PedidoRepository;
import br.com.senior.avaliacao.repository.ProdutoServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Configuration
public class IniciaBanco {
    @Autowired
    private ProdutoServicoRepository psr;
    @Autowired
    private PedidoRepository pr;

    @Bean
    public void gerarDados() {
        final ProdutoServico ps = criarProdutoServico();
        criarProduto(ps);
    }

    private ProdutoServico criarProdutoServico() {
        ProdutoServico ps = new ProdutoServico();
        ps.setId(UUID.randomUUID());
        ps.setName("Teste Produto");
        ps.setAtivo(true);
        ps.setTipoDado(TipoEnum.PRODUTO);

        return psr.save(ps);
    }

    private void criarProduto(final ProdutoServico ps) {
        Pedido pedido = new Pedido();
        pedido.setId(UUID.randomUUID());
        pedido.setDataPedido(new Date());
        pedido.setAtivo(true);
        pedido.setValor(BigDecimal.ZERO);
        pedido.setValorTotal(BigDecimal.ZERO);

        pedido.setItemPedidoList(new ArrayList<>());
        pedido.getItemPedidoList().add(criarItem(pedido, ps));

        pedido.getItemPedidoList()
                .stream().map(itemPedido -> {
            pedido.setValor(pedido.getValor().add(itemPedido.getValorTotal()));
            pedido.setValorTotal(pedido.getValorTotal().add(itemPedido.getValorTotal()));
            return null;
        });

        pr.save(pedido);
    }

    private ItemPedido criarItem(final Pedido pedido, final ProdutoServico ps) {
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setValor(BigDecimal.valueOf(10.5));
        itemPedido.setQtd(2);
        itemPedido.setValorTotal(BigDecimal.valueOf(itemPedido.getValor().doubleValue() * itemPedido.getQtd()));
        itemPedido.setPedido(pedido);
        itemPedido.setProdutoServico(ps);

        return itemPedido;
    }

}
