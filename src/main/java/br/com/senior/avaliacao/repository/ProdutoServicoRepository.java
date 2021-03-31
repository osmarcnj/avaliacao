package br.com.senior.avaliacao.repository;

import br.com.senior.avaliacao.model.ProdutoServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.UUID;

public interface ProdutoServicoRepository extends JpaRepository<ProdutoServico, UUID>{

    @Query("select ps from produtoservico ps where ps.name like %?1%")
    List<ProdutoServico> findByName(String name);

}
