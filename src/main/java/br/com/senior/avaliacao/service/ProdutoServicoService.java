package br.com.senior.avaliacao.service;

import br.com.senior.avaliacao.model.ProdutoServico;
import br.com.senior.avaliacao.repository.ProdutoServicoRepository;
import br.com.senior.avaliacao.requests.ProdutoServicoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProdutoServicoService {

    private final ProdutoServicoRepository repository;

    public List<ProdutoServico> listAll(){
        return repository.findAll();
    }

    public ProdutoServico save(ProdutoServicoDTO produtoServicoDTO) {
        return repository.save(ProdutoServico.builder().name(produtoServicoDTO.getName()).build());
    }

    public ProdutoServico findByIdOrThrowBadRequestExceprion(UUID id){
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not found"));
    }


}
