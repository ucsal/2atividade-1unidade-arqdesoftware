package br.ucsal.visitor;

import java.math.BigDecimal;

public class Medicamento implements ItemNota {

    private final String nome;
    private final BigDecimal valor;
    private final boolean controlado;

    public Medicamento(String nome, BigDecimal valor, boolean controlado) {
        this.nome = nome;
        this.valor = valor;
        this.controlado = controlado;
    }

    public boolean isControlado() {
        return controlado;
    }

    @Override
    public String getDescricao() {
        return nome;
    }

    @Override
    public BigDecimal getValor() {
        return valor;
    }

    @Override
    public <R> R aceitar(VisitanteItem<R> visitante) {
        return visitante.visitarMedicamento(this);
    }
}
