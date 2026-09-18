package br.ucsal.decorator;

import java.math.BigDecimal;

public class PagamentoPix implements Cobranca {

    private final String pedido;
    private final BigDecimal valor;

    public PagamentoPix(String pedido, BigDecimal valor) {
        this.pedido = pedido;
        this.valor = valor;
    }

    @Override
    public BigDecimal getValor() {
        return valor;
    }

    @Override
    public String getDescricao() {
        return "Pedido " + pedido + " via PIX";
    }
}
