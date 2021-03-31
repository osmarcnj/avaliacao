package br.com.senior.avaliacao.repository.custom;

import br.com.senior.avaliacao.model.ProdutoServico;
import br.com.senior.avaliacao.model.QProdutoServico;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;


@Repository
public interface ProdutoServicoRepositoryCustom
        extends JpaRepository<ProdutoServico, String>, QuerydslPredicateExecutor<ProdutoServico> {

}