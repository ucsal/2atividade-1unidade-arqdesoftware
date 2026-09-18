package br.ucsal.decorator;

import java.math.BigDecimal;

// Decorador que subtrai: o padrao nao obriga a somar, so a devolver uma Cobranca valida.
public class CupomDesconto extends CobrancaDecorator {

    private final String codigo;
    private final BigDecimal desconto;

    public CupomDesconto(Cobranca cobrancaEnvolvida, String codigo, BigDecimal desconto) {
        super(cobrancaEnvolvida);
        this.codigo = codigo;
        this.desconto = desconto;
    }

    @Override
    public BigDecimal getValor() {
        BigDecimal base = cobrancaEnvolvida.getValor();
        BigDecimal resultado = base.subtract(desconto);
        return resultado.signum() < 0 ? BigDecimal.ZERO.setScale(2) : resultado;
    }

    @Override
    public String getDescricao() {
        return cobrancaEnvolvida.getDescricao() + " - cupom " + codigo;
    }
}
