package br.ucsal.decorator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TaxaParcelamento extends CobrancaDecorator {

    private static final BigDecimal JUROS_MENSAL = new BigDecimal("0.0249");

    private final int parcelas;

    public TaxaParcelamento(Cobranca cobrancaEnvolvida, int parcelas) {
        super(cobrancaEnvolvida);
        if (parcelas < 1) {
            throw new IllegalArgumentException("Minimo de 1 parcela");
        }
        this.parcelas = parcelas;
    }

    @Override
    public BigDecimal getValor() {
        BigDecimal base = cobrancaEnvolvida.getValor();
        if (parcelas == 1) {
            return base;
        }
        BigDecimal juros = JUROS_MENSAL.multiply(BigDecimal.valueOf(parcelas)).multiply(base);
        return base.add(juros).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getDescricao() {
        return cobrancaEnvolvida.getDescricao() + " + juros em " + parcelas + "x";
    }
}
