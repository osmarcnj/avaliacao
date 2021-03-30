package br.com.senior.avaliacao.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProdutoServicoResponse {

    private UUID id;
    private String name;

    @JsonProperty("tipo_dado")
    private String tipoDado;

    private Boolean ativo;
}
