package br.com.senior.avaliacao.controller;

import br.com.senior.avaliacao.model.Pedido;
import br.com.senior.avaliacao.model.request.PedidoRequest;
import br.com.senior.avaliacao.model.response.ItemPedidoResponse;
import br.com.senior.avaliacao.model.response.PedidoResponse;
import br.com.senior.avaliacao.repository.custom.PedidoRepositoryCustom;
import br.com.senior.avaliacao.service.PedidoService;
import lombok.RequiredArgsConstructor;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value ="/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PedidoController.class);

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PedidoRepositoryCustom repositoryCustom;

    @PostMapping
    public ResponseEntity<PedidoResponse> criar(@Valid @RequestBody PedidoRequest pedidoRequest){
        LOGGER.info("INICIANDO - SALVANDO PEDIDO");

        final Pedido pedido = requestToModel(pedidoRequest);

        LOGGER.info("FINALIZANDO - SALVANDO PEDIDO");
        return new ResponseEntity<>(modelToResponse(pedidoService.criarPedido(pedido)), HttpStatus.CREATED);

    }

    @PutMapping(path = "/alterar")
    public ResponseEntity<PedidoResponse> alterar(@Valid @RequestBody PedidoRequest pedidoRequest){
        LOGGER.info("INICIANDO - ALTERANDO PEDIDO");

        final Pedido pedido = requestToModel(pedidoRequest);
         pedido.setId(UUID.fromString(pedidoRequest.getId()));

        LOGGER.info("FINALIZANDO - ALTERANDO PEDIDO");
        return new ResponseEntity<>(modelToResponse(pedidoService.alterar(pedido)), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/deletar/{id}")
    public ResponseEntity<Void> deletar (@PathVariable String id){
        LOGGER.info("INICIANDO - DELETANDO PEDIDO");

        pedidoService.delete(UUID.fromString(id));

        LOGGER.info("FINALIZANDO - DELETANDO PEDIDO");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> list(){
        List<Pedido> pedidoList = pedidoService.listAll();

        final List<PedidoResponse> response = new ArrayList<>();
        pedidoList.forEach(pedido -> {
            PedidoResponse pr = modelToResponse(pedido);
            pr.setItemPedidoResponseList(new ArrayList<>());

            pedido.getItemPedidoList().forEach(itemPedido -> pr.getItemPedidoResponseList().add(
              modelMapper.map(itemPedido, ItemPedidoResponse.class)
            ));
            response.add(pr);
        });

        return ResponseEntity.ok(response);
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


    @PutMapping(path = "/addItem")
    public ResponseEntity<PedidoResponse> addItem(@Valid @RequestBody PedidoRequest pedidoRequest){
        LOGGER.info("INICIANDO - ADD ITEM");

        final Pedido pedido = requestToModel(pedidoRequest);
        pedido.setId(UUID.fromString(pedidoRequest.getId()));


        LOGGER.info("FINALIZANDO - ADD ITEM");
        return new ResponseEntity<>(modelToResponse(pedidoService.addItem(pedido)), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/removeItem")
    public ResponseEntity<PedidoResponse> removeItem(@Valid @RequestBody PedidoRequest pedidoRequest){
        LOGGER.info("INICIANDO - DELETE ITEM");

        final Pedido pedido = requestToModel(pedidoRequest);
        pedido.setId(UUID.fromString(pedidoRequest.getId()));
        pedidoService.removeItem(pedido);

        LOGGER.info("FINALIZANDO - DELETE ITEM");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //TODO NÃO FUNCIONA AINDA
    @GetMapping(path = "/listaData")
    public ResponseEntity<List<Pedido>> findByData(@RequestParam(value = "data_incial", required = false)
                                                               String data_inicial,
                                                     @RequestParam (value = "data_final", required = false)
                                                             String data_final){
        List pedidoList =new ArrayList();
        try {SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(data_inicial));
            Calendar cal2 = Calendar.getInstance();
            cal.setTime(sdf.parse(data_final));
          //  pedidoList = repositoryCustom.buscaPedidoPorData(new Date(data_inicial), cal2);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(pedidoList);


    }


    private Pedido requestToModel(final PedidoRequest pedidoRequest) {
        return modelMapper.map(pedidoRequest, Pedido.class);
    }

    private PedidoResponse modelToResponse(final Pedido pedido) {
        return modelMapper.map(pedido, PedidoResponse.class);
    }

}
