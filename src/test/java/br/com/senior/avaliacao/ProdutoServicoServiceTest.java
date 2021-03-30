package br.com.senior.avaliacao;

import br.com.senior.avaliacao.enums.TipoEnum;
import br.com.senior.avaliacao.exception.ObjectNotFoundException;
import br.com.senior.avaliacao.model.Pedido;
import br.com.senior.avaliacao.model.ProdutoServico;
import br.com.senior.avaliacao.repository.ProdutoServicoRepository;
import br.com.senior.avaliacao.service.ProdutoServicoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProdutoServicoServiceTest {

    @InjectMocks
    private ProdutoServicoService produtoServicoService;

    @Mock
    private ProdutoServicoRepository repository;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void salvar() {
        final ProdutoServico produto = createProdutoServicoService();

        produtoServicoService.save(produto);

        ArgumentCaptor<ProdutoServico> captor = ArgumentCaptor.forClass(ProdutoServico.class);
        Mockito.verify(repository).save(captor.capture());


        Assert.assertNotNull(captor.getValue());

        final ProdutoServico valor = captor.getValue();
        Assert.assertNotNull(valor.getId());
        Assert.assertEquals(produto.getName(), valor.getName());
        Assert.assertEquals(produto.getTipoDado(), valor.getTipoDado());
        Assert.assertEquals(produto.getAtivo(), valor.getAtivo());
    }

    @Test
    public void listar() {
        final ProdutoServico produto = createProdutoServicoService();
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(produto));

        final List<ProdutoServico> lista = produtoServicoService.listAll();

        Assert.assertEquals(1, lista.size());
    }

    @Test
    public void porId() {
        final ProdutoServico produto = createProdutoServicoService();
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(produto));

        ProdutoServico produtoRetorno = produtoServicoService.findByIdOrThrowBadRequestException(UUID.randomUUID());

        Assert.assertEquals(produto, produtoRetorno);
    }

    @Test
    public void porNome(){
        final ProdutoServico produto = createProdutoServicoService();
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(produto));

        List<ProdutoServico> lista = produtoServicoService.listPorName(produto.getName());

        Assert.assertEquals(1, lista.size());

    }

    @Test(expected = ObjectNotFoundException.class)
    public void porIdException() {
        Mockito.when(repository.findById(Mockito.any())).thenThrow(new ObjectNotFoundException("Teste"));

        produtoServicoService.findByIdOrThrowBadRequestException(UUID.randomUUID());
    }

    @Test
    public void delete() {
        final ProdutoServico produtoServico = createProdutoServicoService();
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(produtoServico));
        produtoServicoService.delete(produtoServico.getId());

        final List<ProdutoServico> lista = produtoServicoService.listAll();
        Assert.assertEquals(0, lista.size());
    }

    private ProdutoServico createProdutoServicoService() {
        ProdutoServico request = new ProdutoServico();
        request.setName("TESTE");
        request.setTipoDado(TipoEnum.PRODUTO);
        request.setAtivo(true);
        return request;
    }
}
