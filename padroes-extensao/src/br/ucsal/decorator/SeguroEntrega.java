package br.ucsal.decorator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SeguroEntrega extends CobrancaDecorator {

    private static final BigDecimal PERCENTUAL = new BigDecimal("0.015");
    private static final BigDecimal TETO = new BigDecimal("80.00");

    public SeguroEntrega(Cobranca cobrancaEnvolvida) {
        super(cobrancaEnvolvida);
    }

    @Override
    public BigDecimal getValor() {
        BigDecimal base = cobrancaEnvolvida.getValor();
        BigDecimal premio = base.multiply(PERCENTUAL).setScale(2, RoundingMode.HALF_UP);
        if (premio.compareTo(TETO) > 0) {
            premio = TETO;
        }
        return base.add(premio);
    }

    @Override
    public String getDescricao() {
        return cobrancaEnvolvida.getDescricao() + " + seguro";
    }
}
