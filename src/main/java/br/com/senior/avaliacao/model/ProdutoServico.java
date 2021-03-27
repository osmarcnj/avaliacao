package br.com.senior.avaliacao.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "produtoServico")
@Table(name = "produtoServico")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoServico {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, nullable = false)
    private UUID id;

    private String name;


}
