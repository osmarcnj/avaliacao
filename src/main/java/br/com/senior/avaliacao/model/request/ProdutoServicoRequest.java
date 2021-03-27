package br.com.senior.avaliacao.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProdutoServicoRequest {

    private String id;

    @NotEmpty(message = "OBRIGATORIO INFORMAR O NOME")
    @Size(max = 150, message = "NAO PODE CONTER MAIS QUE 150 CARACTERES")
    private String name ;

    @JsonProperty("tipo_dado")
    @NotBlank(message = "OBRIGATORIO INFORMAR O TIPO DO DADO")
    private String tipoDado;

    @NotNull(message = "OBRIGADO INFORMAR")
    private Boolean ativo;

}
