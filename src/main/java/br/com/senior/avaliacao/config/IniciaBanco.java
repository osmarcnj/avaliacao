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
        final ProdutoServico produto = criarProdutoServico(TipoEnum.PRODUTO);
        final ProdutoServico servico = criarProdutoServico(TipoEnum.SERVICO);

        Pedido pedido = new Pedido();

        criarPedido(pedido, produto, 2, BigDecimal.valueOf(10.2), 10.0);
        criarPedido(pedido, servico, 3, BigDecimal.valueOf(33.33), 10.0);
    }

    private ProdutoServico criarProdutoServico(final TipoEnum tipoEnum) {
        ProdutoServico ps = new ProdutoServico();
        ps.setId(UUID.randomUUID());
        ps.setName("Teste " + tipoEnum.name());
        ps.setAtivo(true);
        ps.setTipoDado(tipoEnum);

        return psr.save(ps);
    }
    //TODO - LEVAR PARA PEDIDOSERVICE
    private void criarPedido(final Pedido pedido, final ProdutoServico ps,
            final int qtd, final BigDecimal valor, final double desc) {
        pedido.setId(UUID.randomUUID());
        pedido.setDataPedido(new Date());
        pedido.setAtivo(true);
        pedido.setValor(BigDecimal.ZERO);
        pedido.setDesconto(BigDecimal.ZERO);
        pedido.setValorTotal(BigDecimal.ZERO);

        pedido.setItemPedidoList(new ArrayList<>());
        pedido.getItemPedidoList().add(criarItem(pedido, ps, qtd, valor));

        calcularTotalPedido(pedido, desc);

        pr.save(pedido);
    }

    private void calcularTotalPedido(final Pedido pedido, double desc) {
        pedido.getItemPedidoList()
                .forEach(itemPedido -> {
            pedido.setValor(pedido.getValor().add(itemPedido.getValorTotal()));
            gerarDesconto(itemPedido, desc);
            pedido.setValorTotal(pedido.getValorTotal().add(itemPedido.getValorTotal()));
        });

    }

    private ItemPedido criarItem(final Pedido pedido, final ProdutoServico ps,
            final int qtd, final BigDecimal valor) {
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setQtd(qtd);
        itemPedido.setValor(valor);
        itemPedido.setValorTotal(BigDecimal.valueOf(itemPedido.getValor().doubleValue() * itemPedido.getQtd()));
        itemPedido.setPedido(pedido);
        itemPedido.setProdutoServico(ps);

       return itemPedido;
    }

    private void gerarDesconto(ItemPedido ip, double desc) {
        if(desc > 0 && ip.getProdutoServico().getTipoDado().equals(TipoEnum.PRODUTO)) {
            var valorDesc = (ip.getValorTotal().doubleValue() * desc) /100;
            ip.setDesconto(BigDecimal.valueOf(valorDesc));
            ip.setValorTotal(ip.getValorTotal().subtract(BigDecimal.valueOf(valorDesc)));
            ip.getPedido().setDesconto(ip.getPedido().getDesconto().add(ip.getDesconto()));
        }
    }

}
