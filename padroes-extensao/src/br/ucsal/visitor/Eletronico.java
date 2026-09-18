package br.ucsal.visitor;

import java.math.BigDecimal;

public class Eletronico implements ItemNota {

    private final String modelo;
    private final BigDecimal valor;
    private final boolean fragil;

    public Eletronico(String modelo, BigDecimal valor, boolean fragil) {
        this.modelo = modelo;
        this.valor = valor;
        this.fragil = fragil;
    }

    public boolean isFragil() {
        return fragil;
    }

    @Override
    public String getDescricao() {
        return modelo;
    }

    @Override
    public BigDecimal getValor() {
        return valor;
    }

    @Override
    public <R> R aceitar(VisitanteItem<R> visitante) {
        return visitante.visitarEletronico(this);
    }
}
