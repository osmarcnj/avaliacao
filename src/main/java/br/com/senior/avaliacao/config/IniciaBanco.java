package br.com.senior.avaliacao.config;

import br.com.senior.avaliacao.Util.UtilSetting;
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
        final ProdutoServico produto = criarProdutoServico(
                TipoEnum.PRODUTO, new BigDecimal(50.00), "efb40958-214a-40bb-b151-c972c29f009f");
        final ProdutoServico servico = criarProdutoServico(
                TipoEnum.SERVICO, new BigDecimal(10.00), "b0acd107-8192-4ecf-9914-844337320263");

        Pedido pedido = new Pedido();


        criarPedido(pedido, produto, 2, produto.getValor(),
                10.0, "e2a9b2f2-4099-47bf-822d-7054aac2c9a6",
                "6e6fa78c-1eba-44ee-a3b6-f766c4684123");
        criarPedido(pedido, servico, 3, servico.getValor(),
                10.0, "238df219-339e-4cc5-aeef-b6fc0928041d",
                "2175bcb8-8a2a-47d0-a738-64b3687cc0e");
    }

    private ProdutoServico criarProdutoServico(final TipoEnum tipoEnum, BigDecimal valor, String id) {

        ProdutoServico ps = new ProdutoServico();
        ps.setId(UUID.fromString(id));


        ps.setName("Teste " + tipoEnum.name());
        ps.setAtivo(true);
        ps.setTipoDado(tipoEnum);
        ps.setValor(new BigDecimal(10.00));

        return psr.save(ps);
    }
    //TODO - LEVAR PARA PEDIDOSERVICE
    private void criarPedido(final Pedido pedido, final ProdutoServico ps,
            final int qtd, final BigDecimal valor, final double desc, String id, String idItem) {
        pedido.setId(UUID.fromString(id));
        pedido.setDataPedido(new Date());
        pedido.setAtivo(true);
        pedido.setValor(BigDecimal.ZERO);
        pedido.setDesconto(new BigDecimal(desc));
        pedido.setValorTotal(BigDecimal.ZERO);

        pedido.setItemPedidoList(new ArrayList<>());
        pedido.getItemPedidoList().add(UtilSetting.criarItem(pedido, ps, qtd));
        pedido.getItemPedidoList().get(0).setId(UUID.fromString(idItem));


        pr.save(UtilSetting.calcularTotalPedido(pedido));
    }



}
