package br.com.senior.avaliacao;

import br.com.senior.avaliacao.enums.TipoEnum;
import br.com.senior.avaliacao.exception.ObjectNotFoundException;
import br.com.senior.avaliacao.model.Pedido;
import br.com.senior.avaliacao.model.ProdutoServico;
import br.com.senior.avaliacao.repository.PedidoRepository;
import br.com.senior.avaliacao.repository.ProdutoServicoRepository;
import br.com.senior.avaliacao.service.PedidoService;
import br.com.senior.avaliacao.service.ProdutoServicoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.*;

public class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository repository;

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






    private Pedido createPedidoService() {
        Pedido request = new Pedido();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2021);
        c.set(Calendar.MONTH, Calendar.MARCH);
        c.set(Calendar.DAY_OF_MONTH, 29);
        request.setDataPedido(c);
        request.setAtivo(true);
        return request;
    }
}
