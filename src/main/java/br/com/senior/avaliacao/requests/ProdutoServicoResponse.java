package br.com.senior.avaliacao.requests;

import lombok.Data;

import java.util.UUID;

@Data
public class ProdutoServicoResponse {

    private UUID id;
    private String name;
}
