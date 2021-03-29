package br.com.senior.avaliacao.service;

import br.com.senior.avaliacao.exception.AvaliacaoException;
import br.com.senior.avaliacao.exception.ObjectNotFoundException;
import br.com.senior.avaliacao.model.Pedido;
import br.com.senior.avaliacao.model.ProdutoServico;
import br.com.senior.avaliacao.repository.PedidoRepository;
import br.com.senior.avaliacao.repository.ProdutoServicoRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PedidoService.class);

    @Autowired
    private PedidoRepository repository;

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
                pedido.setDataPedido(Calendar.getInstance());
            }

            return repository.save(pedido);
        } catch (Exception e) {
            LOGGER.error("ERRO AO SALVAR PEDIDO {}", e);
            throw new AvaliacaoException("OCORREU UM ERRO AO SALVAR O PEDIDO ", e);
        }
    }

    public Pedido findByIdOrThrowBadRequestException(UUID id){
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("OBJETO NAO ENCONTRADO COM O UUID : " + id));
    }

    public void delete(UUID id) {
        repository.delete(findByIdOrThrowBadRequestException(id));
    }



}
