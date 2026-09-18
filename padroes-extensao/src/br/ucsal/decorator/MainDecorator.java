package br.ucsal.decorator;

import java.math.BigDecimal;
import java.util.Locale;

public class MainDecorator {

    private static final Locale BR = Locale.forLanguageTag("pt-BR");

    public static void main(String[] args) {
        System.out.println("=== DECORATOR: gateway de pagamentos ===\n");

        Cobranca simples = new PagamentoPix("1001", new BigDecimal("250.00"));
        imprimir(simples);

        Cobranca completa =
                new SeguroEntrega(
                    new TaxaParcelamento(
                        new CupomDesconto(
                            new PagamentoCartao("1002", "VISA", new BigDecimal("1800.00")),
                            "VOLTA200", new BigDecimal("200.00")),
                        6));
        imprimir(completa);

        Cobranca outraOrdem =
                new CupomDesconto(
                    new SeguroEntrega(
                        new TaxaParcelamento(
                            new PagamentoCartao("1003", "VISA", new BigDecimal("1800.00")),
                            6)),
                    "VOLTA200", new BigDecimal("200.00"));
        imprimir(outraOrdem);

        System.out.println("Os dois ultimos usam os mesmos decoradores sobre a mesma");
        System.out.println("compra, em ordens diferentes, e chegam a valores diferentes.");
    }

    // Recebe apenas Cobranca: nao sabe se ha um pagamento cru ou tres decoradores empilhados.
    private static void imprimir(Cobranca cobranca) {
        System.out.println(cobranca.getDescricao());
        System.out.printf(BR, "  Total: R$ %,.2f%n%n", cobranca.getValor());
    }
}
