package br.ucsal.decorator;

import java.math.BigDecimal;

public class PagamentoCartao implements Cobranca {

    private final String pedido;
    private final String bandeira;
    private final BigDecimal valor;

    public PagamentoCartao(String pedido, String bandeira, BigDecimal valor) {
        this.pedido = pedido;
        this.bandeira = bandeira;
        this.valor = valor;
    }

    @Override
    public BigDecimal getValor() {
        return valor;
    }

    @Override
    public String getDescricao() {
        return "Pedido " + pedido + " no cartao " + bandeira;
    }
}
