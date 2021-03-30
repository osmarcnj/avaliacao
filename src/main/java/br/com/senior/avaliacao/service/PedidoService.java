package br.com.senior.avaliacao.service;

import br.com.senior.avaliacao.Util.UtilSetting;
import br.com.senior.avaliacao.exception.AvaliacaoException;
import br.com.senior.avaliacao.exception.ObjectNotFoundException;
import br.com.senior.avaliacao.model.ItemPedido;
import br.com.senior.avaliacao.model.Pedido;
import br.com.senior.avaliacao.repository.ItemPedidoRepository;
import br.com.senior.avaliacao.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PedidoService.class);

    @Autowired
    private PedidoRepository repository;
    @Autowired
    private UtilSetting util;

    @Autowired
    private ItemPedidoRepository itemRepository;

    public List<Pedido> listAll(){
        return repository.findAll();
    }
    public Page<Pedido> listAll(Pageable pageable){
        return repository.findAll(pageable);
    }


    public Pedido save(Pedido pedido) {
        try {
            if(Objects.isNull(pedido.getId())) {
                pedido.setId(UUID.randomUUID());
            }

            return repository.save(pedido);
        } catch (Exception e) {
            LOGGER.error("ERRO AO SALVAR ITEM DO PEDIDO {}", e);
            throw new AvaliacaoException("OCORREU UM ERRO AO SALVAR O ITEM DO PEDIDO ", e);
        }
    }

    public Pedido findByIdOrThrowBadRequestException(UUID id){
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("OBJETO NAO ENCONTRADO COM O UUID : " + id));
    }

    public void delete(UUID id) {
        repository.delete(findByIdOrThrowBadRequestException(id));
    }

    public Pedido criarPedido(Pedido pedido) {
        try {
        pedido.setId(UUID.randomUUID());
        pedido.setDataPedido(new Date());
        pedido.setAtivo(true);
        pedido.setValor(BigDecimal.ZERO);
        pedido.setValorTotal(BigDecimal.ZERO);
        List<ItemPedido> itens = new ArrayList<>();
        pedido.getItemPedidoList().forEach(itemPedido -> {
            itens.add(util.criarItem(pedido, itemPedido.getProdutoServico(), itemPedido.getQtd()));
            if(!itemPedido.getProdutoServico().getAtivo() && itemPedido.getId() == null) {
                throw new AvaliacaoException("IMPOSSÍVEL SALVAR PRODUTO INATIVO ");
            }
        });

        pedido.setItemPedidoList(itens);
       return repository.save(util.calcularTotalPedido(pedido));

        } catch (AvaliacaoException a){
            throw new AvaliacaoException("IMPOSSÍVEL SALVAR PRODUTO INATIVO ");
        }catch (Exception e) {
            LOGGER.error("ERRO AO SALVAR PEDIDO {}", e);
            throw new AvaliacaoException("OCORREU UM ERRO AO SALVAR O PEDIDO ", e);
        }
    }

    public Pedido alterar(Pedido pedido) {
        try {

            return repository.save(pedido);
        } catch (Exception e) {
            LOGGER.error("ERRO AO SALVAR PEDIDO {}", e);
            throw new AvaliacaoException("OCORREU UM ERRO AO SALVAR O PEDIDO ", e);
        }
    }
    public Pedido addItem(Pedido pedido) {
        try {

            Pedido finalPedido = pedido;
            pedido.getItemPedidoList().forEach(itemPedido -> {
                    itemPedido.setPedido(finalPedido);
                if(!itemPedido.getProdutoServico().getAtivo() && itemPedido.getId() == null) {
                    throw new AvaliacaoException("IMPOSSÍVEL SALVAR PRODUTO INATIVO ");
                }
                if(Objects.isNull(itemPedido.getId())) {
                    itemPedido.setId(UUID.randomUUID());
                }
            });
            pedido = (pedido.getAtivo()?util.calcularTotalPedido(pedido): pedido );

            return repository.save(pedido);
        } catch (AvaliacaoException a){
            throw new AvaliacaoException("IMPOSSÍVEL SALVAR PRODUTO INATIVO ");
        }catch (Exception e) {
            LOGGER.error("ERRO AO SALVAR PEDIDO {}", e);
            throw new AvaliacaoException("OCORREU UM ERRO AO SALVAR O PEDIDO ", e);
        }
    }

    public void removeItem(Pedido pedido) {
        try {
            pedido.getItemPedidoList().forEach(itemPedido -> {
                itemPedido.setPedido(pedido);

            });
            itemRepository.deleteAll(pedido.getItemPedidoList());
        } catch (Exception e) {
            LOGGER.error("ERRO AO SALVAR PEDIDO {}", e);
            throw new AvaliacaoException("OCORREU UM ERRO AO EXCLUIR ITEM ", e);
        }
    }


}
