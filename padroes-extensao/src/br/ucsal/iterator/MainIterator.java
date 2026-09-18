package br.ucsal.iterator;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Locale;

public class MainIterator {

    private static final Locale BR = Locale.forLanguageTag("pt-BR");

    public static void main(String[] args) {
        System.out.println("=== ITERATOR: loja virtual ===\n");

        CarrinhoDeCompras carrinho = new CarrinhoDeCompras();
        carrinho.adicionar(new Produto("Teclado mecanico", "Periferico", new BigDecimal("389.90")));
        carrinho.adicionar(new Produto("Monitor 27 polegadas", "Monitor", new BigDecimal("1499.00")));
        carrinho.adicionar(new Produto("Mouse sem fio", "Periferico", new BigDecimal("129.90")));

        HistoricoDeCompras historico = new HistoricoDeCompras();
        historico.registrar(new Produto("Cadeira ergonomica", "Mobiliario", new BigDecimal("1290.00")));
        historico.registrar(new Produto("Webcam Full HD", "Periferico", new BigDecimal("219.90")));
        historico.registrar(new Produto("HD externo 2TB", "Armazenamento", new BigDecimal("459.00")));

        System.out.println("Carrinho (array por dentro):");
        listar(carrinho);

        System.out.println("Historico (lista ligada por dentro):");
        listar(historico);

        System.out.println("Dois percursos simultaneos no mesmo carrinho:");
        Iterator<Produto> a = carrinho.iterator();
        Iterator<Produto> b = carrinho.iterator();
        a.next();
        a.next();
        System.out.println("  percursoA (avancou 2x)   -> " + a.next().getNome());
        System.out.println("  percursoB (recem-criado) -> " + b.next().getNome());
        System.out.println("\nA posicao mora no iterador, nunca na colecao.");
    }

    // Recebe Iterable, entao serve para as duas colecoes apesar de elas nao terem nada em comum por dentro.
    private static void listar(Iterable<Produto> colecao) {
        BigDecimal total = BigDecimal.ZERO;
        for (Produto produto : colecao) {
            System.out.printf(BR, "  %-24s R$ %,9.2f%n", produto.getNome(), produto.getPreco());
            total = total.add(produto.getPreco());
        }
        System.out.printf(BR, "  %-24s R$ %,9.2f%n%n", "SUBTOTAL", total);
    }
}
