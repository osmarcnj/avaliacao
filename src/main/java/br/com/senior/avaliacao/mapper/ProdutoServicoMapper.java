package br.com.senior.avaliacao.mapper;

import br.com.senior.avaliacao.model.ProdutoServico;
import br.com.senior.avaliacao.requests.ProdutoServicoDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProdutoServicoMapper {

    public static final ProdutoServicoMapper  INSTANCE = Mappers.getMapper(ProdutoServicoMapper.class);

   // public abstract ProdutoServico toProdutoServico (ProdutoServicoDTO produtoServicoDTO);

    ProdutoServico toModel(ProdutoServicoDTO produtoServicoDTO);

    @InheritInverseConfiguration
    public abstract ProdutoServicoDTO toProdutoServicoDTO (ProdutoServico produtoServico);
}
