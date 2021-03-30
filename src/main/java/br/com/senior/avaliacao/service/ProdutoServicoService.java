package br.com.senior.avaliacao.service;

import br.com.senior.avaliacao.exception.AvaliacaoException;
import br.com.senior.avaliacao.exception.ObjectNotFoundException;
import br.com.senior.avaliacao.model.ProdutoServico;
import br.com.senior.avaliacao.repository.ProdutoServicoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProdutoServicoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoServicoService.class);

	@Autowired
    private ProdutoServicoRepository repository;

    public List<ProdutoServico> listAll(){
        return repository.findAll();
    }

    public Page<ProdutoServico> listAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public List<ProdutoServico> listPorName(String name){
        return repository.findByName(name);
    }

    public ProdutoServico save(ProdutoServico produtoServico) {
        try {
            if(Objects.isNull(produtoServico.getId())) {
                produtoServico.setId(UUID.randomUUID());
            }
            return repository.save(produtoServico);
        } catch (Exception e) {
            LOGGER.error("ERRO AO SALVAR PRODUTO {}", e);
            throw new AvaliacaoException("OCORREU UM ERRO AO SALVAR O PRODUTO ", e);
        }
    }

    public ProdutoServico findByIdOrThrowBadRequestException(UUID id){
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("OBJETO NAO ENCONTRADO COM O UUID : " + id));
    }

    public void delete(UUID id)  {
            try{
                repository.delete(findByIdOrThrowBadRequestException(id));
            }catch (DataIntegrityViolationException e){
                throw new AvaliacaoException("PRODUTO / SERVIÇO NÃO PODE SER DELETADO", e);
            }


    }

}
