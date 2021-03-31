package br.com.senior.avaliacao;

import br.com.senior.avaliacao.Util.UtilSetting;
import br.com.senior.avaliacao.enums.TipoEnum;
import br.com.senior.avaliacao.exception.ObjectNotFoundException;
import br.com.senior.avaliacao.model.ItemPedido;
import br.com.senior.avaliacao.model.Pedido;
import br.com.senior.avaliacao.model.ProdutoServico;
import br.com.senior.avaliacao.repository.ItemPedidoRepository;
import br.com.senior.avaliacao.repository.PedidoRepository;
import br.com.senior.avaliacao.repository.ProdutoServicoRepository;
import br.com.senior.avaliacao.service.ItemPedidoService;
import br.com.senior.avaliacao.service.PedidoService;
import br.com.senior.avaliacao.service.ProdutoServicoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.*;

public class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository repository;

    @InjectMocks
    private ProdutoServicoService produtoServicoService;

    @Mock
    private ProdutoServicoRepository produtoServicoRepository;

    @InjectMocks
    private ItemPedidoService itemPedidoService;

    @Mock
    private ItemPedidoRepository itemPedidoRepository;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void salvar() {
        final Pedido pedido = createPedidoService();

        pedidoService.save(pedido);

        ArgumentCaptor<Pedido> captor = ArgumentCaptor.forClass(Pedido.class);
        Mockito.verify(repository).save(captor.capture());


        Assert.assertNotNull(captor.getValue());

        final Pedido valor = captor.getValue();
        Assert.assertNotNull(valor.getId());
        Assert.assertEquals(pedido.getDataPedido(), pedido.getDataPedido());
        Assert.assertEquals(pedido.getAtivo(), valor.getAtivo());
    }

    @Test
    public void listar() {
        final Pedido pedido = createPedidoService();
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(pedido));

        final List<Pedido> lista = pedidoService.listAll();

        Assert.assertEquals(1, lista.size());
    }

    @Test
    public void porId() {
        final Pedido pedido = createPedidoService();
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(pedido));

        Pedido pedidoRetorno = pedidoService.findByIdOrThrowBadRequestException(UUID.randomUUID());

        Assert.assertEquals(pedido, pedidoRetorno);
    }

    @Test(expected = ObjectNotFoundException.class)
    public void porIdException() {
        Mockito.when(repository.findById(Mockito.any())).thenThrow(new ObjectNotFoundException("Teste"));

        pedidoService.findByIdOrThrowBadRequestException(UUID.randomUUID());
    }

    @Test
    public void delete() {
        final Pedido pedido = createPedidoService();
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(pedido));
        pedidoService.delete(pedido.getId());

        final List<Pedido> lista = pedidoService.listAll();
        Assert.assertEquals(0, lista.size());
    }

    @Test
    public void alterar() {
        final Pedido pedido = createPedidoService();
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(pedido));
        pedido.setAtivo(false);
        pedidoService.alterar(pedido);

        Pedido pedidoRetorno = pedidoService.findByIdOrThrowBadRequestException(UUID.randomUUID());

        Assert.assertFalse(pedidoRetorno.getAtivo());

    }
    @Test
    public void addItem(){
        final ProdutoServico produto = createProdutoServicoService();
        produtoServicoService.save(produto);
        final Pedido pedido = createPedidoService();
        pedidoService.save(pedido);
        ArgumentCaptor<Pedido> captor = ArgumentCaptor.forClass(Pedido.class);
        Mockito.verify(repository).save(captor.capture());

        List<ItemPedido> itemPedidoList = new ArrayList<>();
        itemPedidoList.add(createItemPedidService(produto, pedido));
        pedido.setItemPedidoList(itemPedidoList);

        pedidoService.addItem(pedido);

        Assert.assertNotNull(captor.getValue());

        final Pedido valor = captor.getValue();
        Assert.assertNotNull(valor.getId());
        Assert.assertEquals(pedido.getDataPedido(), pedido.getDataPedido());
        Assert.assertEquals(pedido.getAtivo(), valor.getAtivo());
        Assert.assertEquals(pedido.getItemPedidoList().size(), valor.getItemPedidoList().size());

    }

    @Test
    public void removeItem(){
        final ProdutoServico produto = createProdutoServicoService();
        produtoServicoService.save(produto);
        final Pedido pedido = createPedidoService();
        pedidoService.save(pedido);
        ArgumentCaptor<Pedido> captor = ArgumentCaptor.forClass(Pedido.class);
        Mockito.verify(repository).save(captor.capture());

        List<ItemPedido> itemPedidoList = new ArrayList<>();
        itemPedidoList.add(createItemPedidService(produto, pedido));
        pedido.setItemPedidoList(itemPedidoList);
        pedidoService.save(pedido);

        pedidoService.removeItem(pedido);



        Assert.assertNotNull(captor.getValue());

        final Pedido valor = captor.getValue();
        Assert.assertNotNull(valor.getId());
        Assert.assertEquals(pedido.getDataPedido(), pedido.getDataPedido());
        Assert.assertEquals(pedido.getAtivo(), valor.getAtivo());
        Assert.assertEquals(pedido.getItemPedidoList().size(), valor.getItemPedidoList().size());

    }

    private Pedido createPedidoService() {
        Pedido request = new Pedido();
        request.setDataPedido(new Date());
        request.setAtivo(true);
        request.setValor(new BigDecimal(30));
        request.setValor(new BigDecimal(27));
        request.setDesconto(new BigDecimal(10));
        return request;
    }

    private ItemPedido createItemPedidService(ProdutoServico produtoServico, Pedido pedido) {
        ItemPedido item = new ItemPedido();
        item.setProdutoServico(produtoServico);
        item.setQtd(3);
        item.setValor(new BigDecimal(30));
        item.setValorTotal(new BigDecimal(30));
        item.setPedido(pedido);
        return item;
    }
    private ProdutoServico createProdutoServicoService() {
        ProdutoServico request = new ProdutoServico();
        request.setName("TESTE");
        request.setTipoDado(TipoEnum.PRODUTO);
        request.setAtivo(true);
        request.setValor(new BigDecimal(10));
        return request;
    }
}
