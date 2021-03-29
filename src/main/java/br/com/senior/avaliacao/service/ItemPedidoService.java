package br.com.senior.avaliacao.service;

import br.com.senior.avaliacao.exception.AvaliacaoException;
import br.com.senior.avaliacao.exception.ObjectNotFoundException;
import br.com.senior.avaliacao.model.ItemPedido;
import br.com.senior.avaliacao.repository.ItemPedidoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemPedidoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemPedidoService.class);

    @Autowired
    private ItemPedidoRepository repository;

    public List<ItemPedido> listAll(){
        return repository.findAll();
    }
    public Page<ItemPedido> listAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public ItemPedido save(ItemPedido itemPedido) {
        try {
            if(Objects.isNull(itemPedido.getId())) {
                itemPedido.setId(UUID.randomUUID());
            }

           return repository.save(itemPedido);
        } catch (Exception e) {
            LOGGER.error("ERRO AO SALVAR ITEM DO PEDIDO {}", e);
            throw new AvaliacaoException("OCORREU UM ERRO AO SALVAR O ITEM DO PEDIDO ", e);
        }
    }

    public ItemPedido findByIdOrThrowBadRequestException(UUID id){
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("OBJETO NAO ENCONTRADO COM O UUID : " + id));
    }




    public void delete(UUID id) {
        repository.delete(findByIdOrThrowBadRequestException(id));
    }



}