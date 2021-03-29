package br.com.senior.avaliacao.controller;

import br.com.senior.avaliacao.model.ItemPedido;
import br.com.senior.avaliacao.model.Pedido;
import br.com.senior.avaliacao.model.ProdutoServico;
import br.com.senior.avaliacao.model.request.ItemPedidoRequest;
import br.com.senior.avaliacao.model.response.ItemPedidoResponse;
import br.com.senior.avaliacao.model.response.PedidoResponse;
import br.com.senior.avaliacao.service.ItemPedidoService;
import br.com.senior.avaliacao.service.PedidoService;
import br.com.senior.avaliacao.service.ProdutoServicoService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value ="/itemPedido")
@RequiredArgsConstructor
public class ItemPedidoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoServicoController.class);

    @Autowired
    private ItemPedidoService itemPedidoService;
    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private ProdutoServicoService produtoServicoService;


    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ItemPedidoResponse> criar(@Valid @RequestBody ItemPedidoRequest itemPedidoRequest){
        LOGGER.info("INICIANDO - SALVANDO ITEM DO PEDIDO");


        final ItemPedido itemPedido = requestToModel(itemPedidoRequest);
        itemPedido.setPedido(pedidoService.findByIdOrThrowBadRequestException(
                UUID.fromString(itemPedidoRequest
                        .getPedidoRequest().getId())));
        itemPedido.setProdutoServico(produtoServicoService.findByIdOrThrowBadRequestException(
                UUID.fromString(itemPedidoRequest
                        .getProdutoServicoRequest().getId())));
        final ItemPedidoResponse response = modelToResponse(itemPedidoService.save(itemPedido));

        LOGGER.info("FINALIZANDO - SALVANDO ITEM PEDIDO");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(path = "/alterar")
    public ResponseEntity<ItemPedidoResponse> alterar(@Valid @RequestBody ItemPedidoRequest itemPedidoRequest){
        LOGGER.info("INICIANDO - ALTERANDO  ITEM PEDIDO");

        final ItemPedido itemPedido = requestToModel(itemPedidoRequest);
        itemPedido.setId(UUID.fromString(itemPedidoRequest.getId()));
        final ItemPedidoResponse response = modelToResponse(itemPedidoService.save(itemPedido));

        LOGGER.info("FINALIZANDO - ALTERANDO ITEM DO PEDIDO");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/deletar")
    public ResponseEntity<PedidoResponse> Deletar(@NotNull @RequestBody ItemPedidoRequest itemPedidoRequest){
        LOGGER.info("INICIANDO - DELETANDO ITEM DO PEDIDO");


        itemPedidoService.delete(UUID.fromString(itemPedidoRequest.getId()));

        LOGGER.info("FINALIZANDO - DELETANDO ITEM PEDIDO");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ItemPedidoResponse>> list(){
        List<ItemPedido> itemPedidoList = itemPedidoService.listAll();

        return ResponseEntity.ok(itemPedidoList.stream().map(this::modelToResponse)
                .collect(Collectors.toList()));
    }

    @GetMapping (path = "/listTeste")
    public ResponseEntity<List<ItemPedido>> listTeste(){
        List<ItemPedido> itemPedidoList = itemPedidoService.listAll();
        for (ItemPedido item: itemPedidoList) {
            LOGGER.info(item.getId().toString());
           LOGGER.error(item.getPedido().getId() + "IdPedido");
            LOGGER.error(item.getProdutoServico().getId() + "IDProduto Serviço");
        }
        return ResponseEntity.ok(itemPedidoList);
    }

    @GetMapping (path = "/listPage")
    public ResponseEntity<Page<ItemPedido>> listPage(Pageable pageable){
        return ResponseEntity.ok(itemPedidoService.listAll(pageable));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ItemPedidoResponse> findById(@PathVariable String id){
        final ItemPedido itemPedido = itemPedidoService.findByIdOrThrowBadRequestException(UUID.fromString(id));
        return new ResponseEntity<>(modelToResponse(itemPedido), HttpStatus.OK);
    }

    private ItemPedido requestToModel(final ItemPedidoRequest itemPedidoRequest) {
        return modelMapper.map(itemPedidoRequest, ItemPedido.class);
    }

    private ItemPedidoResponse modelToResponse(final ItemPedido itemPedido) {
        return modelMapper.map(itemPedido, ItemPedidoResponse.class);
    }
}
