package br.com.senior.avaliacao.repository.custom;

import br.com.senior.avaliacao.model.Pedido;
import br.com.senior.avaliacao.model.QPedido;
import com.querydsl.core.types.Expression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Repository
public class PedidoRepositoryCustom {
    @Autowired
    private final EntityManager em;

    public List<Pedido> buscaPedidoPorData(Calendar dataInicial, Calendar dataFinal){

        QPedido pedido = QPedido.pedido;
        JPAQuery query = new JPAQuery(em);
        query.from(pedido);
        query.where(pedido.dataPedido.goe((Expression<Date>) dataInicial).and
                (pedido.dataPedido.loe((Expression<Date>) dataFinal)));
        return query.fetch();
    }
}
