package br.ucsal.visitor;

import java.math.BigDecimal;

public interface ItemNota {

    String getDescricao();

    BigDecimal getValor();

    <R> R aceitar(VisitanteItem<R> visitante);
}
