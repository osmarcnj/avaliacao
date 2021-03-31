package br.com.senior.avaliacao.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity(name = "itemPedido")
@Table(name = "itemPedido")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, nullable = false)
    private UUID id;

    private BigDecimal valor;
    private BigDecimal desconto;
    private BigDecimal valorTotal;
    private Integer qtd;

    @ManyToOne
    private Pedido pedido;

    @ManyToOne(cascade = CascadeType.ALL)
    private ProdutoServico produtoServico;

}
