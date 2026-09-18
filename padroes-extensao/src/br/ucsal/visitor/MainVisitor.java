package br.ucsal.visitor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainVisitor {

    private static final Locale BR = Locale.forLanguageTag("pt-BR");

    public static void main(String[] args) {
        System.out.println("=== VISITOR: nota fiscal ===\n");

        List<ItemNota> nota = new ArrayList<>();
        nota.add(new Livro("Padroes de Projeto", "978-8573076103", new BigDecimal("298.00")));
        nota.add(new Eletronico("Notebook Inspiron 15", new BigDecimal("4200.00"), true));
        nota.add(new Eletronico("Mouse sem fio", new BigDecimal("389.90"), false));
        nota.add(new Medicamento("Clonazepam 2mg", new BigDecimal("32.50"), true));
        nota.add(new Medicamento("Dipirona 500mg", new BigDecimal("18.90"), false));

        CalculadoraImposto imposto = new CalculadoraImposto();
        InstrucaoEmbalagem embalagem = new InstrucaoEmbalagem();

        BigDecimal totalImposto = BigDecimal.ZERO;
        for (ItemNota item : nota) {
            BigDecimal impostoDoItem = item.aceitar(imposto);
            totalImposto = totalImposto.add(impostoDoItem);

            System.out.printf(BR, "%-24s valor R$ %,9.2f   imposto R$ %,8.2f%n",
                    item.getDescricao(), item.getValor(), impostoDoItem);
            System.out.println("    " + item.aceitar(embalagem));
        }

        System.out.printf(BR, "%nTotal de tributos: R$ %,.2f%n%n", totalImposto);
        System.out.println("Duas operacoes diferentes sobre os mesmos objetos, sem");
        System.out.println("nenhum instanceof e sem alterar as classes de item.");
    }
}
