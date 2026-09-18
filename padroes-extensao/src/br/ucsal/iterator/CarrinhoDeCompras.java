package br.ucsal.iterator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CarrinhoDeCompras implements Iterable<Produto> {

    private Produto[] itens = new Produto[4];
    private int quantidade = 0;

    public void adicionar(Produto produto) {
        if (quantidade == itens.length) {
            itens = Arrays.copyOf(itens, itens.length * 2);
        }
        itens[quantidade++] = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    @Override
    public Iterator<Produto> iterator() {
        return new IteradorDoCarrinho();
    }

    // Classe interna: so ela conhece o array e o contador.
    private class IteradorDoCarrinho implements Iterator<Produto> {

        private int posicao = 0;

        @Override
        public boolean hasNext() {
            return posicao < quantidade;
        }

        @Override
        public Produto next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Carrinho ja percorrido");
            }
            return itens[posicao++];
        }
    }
}
