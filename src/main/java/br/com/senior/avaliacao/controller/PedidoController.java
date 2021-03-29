package br.com.senior.avaliacao.controller;

import br.com.senior.avaliacao.model.Pedido;
import br.com.senior.avaliacao.model.request.PedidoRequest;
import br.com.senior.avaliacao.model.response.PedidoResponse;
import br.com.senior.avaliacao.service.PedidoService;
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
@RequestMapping(value ="/pedido")
@RequiredArgsConstructor
public class PedidoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoServicoController.class);

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<PedidoResponse> criar(@Valid @RequestBody PedidoRequest pedidoRequest){
        LOGGER.info("INICIANDO - SALVANDO PEDIDO");

        final Pedido pedido = requestToModel(pedidoRequest);
        final PedidoResponse response = modelToResponse(pedidoService.save(pedido));

        LOGGER.info("FINALIZANDO - SALVANDO PEDIDO");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(path = "/alterar")
    public ResponseEntity<PedidoResponse> alterar(@Valid @RequestBody PedidoRequest pedidoRequest){
        LOGGER.info("INICIANDO - ALTERANDO PEDIDO");

        final Pedido pedido = requestToModel(pedidoRequest);
        pedido.setId(UUID.fromString(pedidoRequest.getId()));
        final PedidoResponse response = modelToResponse(pedidoService.save(pedido));

        LOGGER.info("FINALIZANDO - ALTERANDO PEDIDO");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/deletar")
    public ResponseEntity<PedidoResponse> Deletar(@NotNull @RequestBody PedidoRequest pedidoRequest){
        LOGGER.info("INICIANDO - DELETANDO PEDIDO");


        pedidoService.delete(UUID.fromString(pedidoRequest.getId()));

        LOGGER.info("FINALIZANDO - DELETANDO PEDIDO");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> list(){
        List<Pedido> pedidoList = pedidoService.listAll();

        return ResponseEntity.ok(pedidoList.stream().map(this::modelToResponse)
                .collect(Collectors.toList()));
    }

    @GetMapping (path = "/listPage")
    public ResponseEntity<Page<Pedido>> listPage(Pageable pageable){
        return ResponseEntity.ok(pedidoService.listAll(pageable));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PedidoResponse> findById(@PathVariable String id){
        final Pedido pedido = pedidoService.findByIdOrThrowBadRequestException(UUID.fromString(id));
        return new ResponseEntity<>(modelToResponse(pedido), HttpStatus.OK);
    }

    private Pedido requestToModel(final PedidoRequest pedidoRequest) {
        return modelMapper.map(pedidoRequest, Pedido.class);
    }

    private PedidoResponse modelToResponse(final Pedido pedido) {
        return modelMapper.map(pedido, PedidoResponse.class);
    }
}
