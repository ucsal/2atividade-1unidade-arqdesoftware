package br.ucsal.visitor;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculadoraImposto implements VisitanteItem<BigDecimal> {

    @Override
    public BigDecimal visitarLivro(Livro livro) {
        return BigDecimal.ZERO.setScale(2);   // imunidade constitucional
    }

    @Override
    public BigDecimal visitarEletronico(Eletronico eletronico) {
        return arredondar(eletronico.getValor().multiply(new BigDecimal("0.25")));
    }

    @Override
    public BigDecimal visitarMedicamento(Medicamento medicamento) {
        BigDecimal aliquota = medicamento.isControlado()
                ? new BigDecimal("0.12")
                : new BigDecimal("0.08");
        return arredondar(medicamento.getValor().multiply(aliquota));
    }

    private BigDecimal arredondar(BigDecimal valor) {
        return valor.setScale(2, RoundingMode.HALF_UP);
    }
}
