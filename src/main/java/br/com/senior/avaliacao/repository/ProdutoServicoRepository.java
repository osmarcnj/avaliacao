package br.com.senior.avaliacao.repository;

import br.com.senior.avaliacao.model.ProdutoServico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProdutoServicoRepository extends JpaRepository<ProdutoServico, UUID> {
}
