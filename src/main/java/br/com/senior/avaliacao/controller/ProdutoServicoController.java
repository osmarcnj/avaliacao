package br.com.senior.avaliacao.controller;

import br.com.senior.avaliacao.model.ProdutoServico;
import br.com.senior.avaliacao.model.request.ProdutoServicoRequest;
import br.com.senior.avaliacao.model.response.ProdutoServicoResponse;
import br.com.senior.avaliacao.service.ProdutoServicoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping (value ="/produtoServico")
@RequiredArgsConstructor
public class ProdutoServicoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoServicoController.class);

	@Autowired
    private ProdutoServicoService produtoServicoService;

	@Autowired
	private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ProdutoServicoResponse> criar(@Valid @RequestBody ProdutoServicoRequest produtoServicoRequest){
        LOGGER.info("INICIANDO - SALVANDO PRODUTO");

        final ProdutoServico produtoServico = requestToModel(produtoServicoRequest);
    	final ProdutoServicoResponse response = modelToResponse(produtoServicoService.save(produtoServico));

    	LOGGER.info("FINALIZANDO - SALVANDO PRODUTO");
    	return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(path = "/alterar")
    public ResponseEntity<ProdutoServicoResponse> alterar(@Valid @RequestBody ProdutoServicoRequest produtoServicoRequest){
        LOGGER.info("INICIANDO - ALTERANDO PRODUTO");

        final ProdutoServico produtoServico = requestToModel(produtoServicoRequest);
    	produtoServico.setId(UUID.fromString(produtoServicoRequest.getId()));
    	final br.com.senior.avaliacao.model.response.ProdutoServicoResponse response = modelToResponse(produtoServicoService.save(produtoServico));

    	LOGGER.info("FINALIZANDO - ALTERANDO PRODUTO");
    	return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoServicoResponse>> list(){
        List<ProdutoServico> produtoServicoRespons = produtoServicoService.listAll();

        return ResponseEntity.ok(produtoServicoRespons.stream().map(this::modelToResponse)
                .collect(Collectors.toList()));
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<ProdutoServicoResponse> findById(@PathVariable String id){
        final ProdutoServico produtoServico = produtoServicoService.findByIdOrThrowBadRequestException(UUID.fromString(id));
        return new ResponseEntity<>(modelToResponse(produtoServico), HttpStatus.OK);
    }

    private ProdutoServico requestToModel(final ProdutoServicoRequest produtoServicoRequest) {
        return modelMapper.map(produtoServicoRequest, ProdutoServico.class);
    }

    private ProdutoServicoResponse modelToResponse(final ProdutoServico produtoServico) {
        return modelMapper.map(produtoServico, ProdutoServicoResponse.class);
    }
}