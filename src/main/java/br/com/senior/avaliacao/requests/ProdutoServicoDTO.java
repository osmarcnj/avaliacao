package br.com.senior.avaliacao.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoServicoDTO {

    private String id;
    private String name ;
}
