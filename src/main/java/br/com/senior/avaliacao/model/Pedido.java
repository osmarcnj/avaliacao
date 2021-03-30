package br.com.senior.avaliacao.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity(name = "pedido")
@Table(name = "pedido")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @NotNull
    private Date dataPedido;
    private BigDecimal valor;
    private BigDecimal desconto;
    private BigDecimal valorTotal;

    @NotNull
    private Boolean ativo;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itemPedidoList;
}
