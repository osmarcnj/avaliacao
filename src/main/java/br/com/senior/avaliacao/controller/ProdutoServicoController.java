package br.com.senior.avaliacao.controller;

import br.com.senior.avaliacao.mapper.ProdutoServicoMapper;
import br.com.senior.avaliacao.model.ProdutoServico;
import br.com.senior.avaliacao.requests.ProdutoServicoDTO;
import br.com.senior.avaliacao.requests.ProdutoServicoResponse;
import br.com.senior.avaliacao.service.ProdutoServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping (name = "produtoServico")
@RequiredArgsConstructor
public class ProdutoServicoController {

    private final ProdutoServicoService produtoServicoService;

    @GetMapping
    public ResponseEntity<List<ProdutoServicoDTO>> list(){
     //   log.info(dateUtil.formatLocalDateTimeToDataBaseStyle(LocalDateTime.now()));
        List<ProdutoServico> produtoServicos = produtoServicoService.listAll();

        return ResponseEntity.ok(produtoServicos.stream().map(produtoServico -> ProdutoServicoMapper.INSTANCE
                .toProdutoServicoDTO(produtoServico))
                .collect(Collectors.toList()));
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<ProdutoServico> findById(@PathVariable UUID id){
        return ResponseEntity.ok(  produtoServicoService.findByIdOrThrowBadRequestExceprion(id));
    }

    @PostMapping
    public ResponseEntity<ProdutoServico> save(@RequestBody ProdutoServicoDTO produtoServicoDTO){
        return new ResponseEntity<>(produtoServicoService.save(produtoServicoDTO), HttpStatus.CREATED);
    }
/*
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        animeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody AnimePutRequestBody animePutRequestBody){
        animeService.repalce(animePutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
*/
}
