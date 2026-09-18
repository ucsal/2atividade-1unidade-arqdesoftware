package br.ucsal.decorator;

import java.math.BigDecimal;

public interface Cobranca {

    BigDecimal getValor();

    String getDescricao();
}
