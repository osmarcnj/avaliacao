package br.com.senior.avaliacao.model;

import br.com.senior.avaliacao.enums.TipoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity(name = "produtoservico")
@Table(name = "produtoservico")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoServico {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @NotBlank
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TipoEnum tipoDado;

    @NotNull
    private Boolean ativo;
}
