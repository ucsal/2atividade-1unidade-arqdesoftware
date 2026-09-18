package br.ucsal.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

// Mesma interface do carrinho, mas lista ligada por dentro e percurso do mais recente ao mais antigo.
public class HistoricoDeCompras implements Iterable<Produto> {

    private static class No {
        private final Produto produto;
        private final No anterior;

        private No(Produto produto, No anterior) {
            this.produto = produto;
            this.anterior = anterior;
        }
    }

    private No maisRecente;

    public void registrar(Produto produto) {
        maisRecente = new No(produto, maisRecente);
    }

    @Override
    public Iterator<Produto> iterator() {
        return new IteradorDoMaisRecente();
    }

    private class IteradorDoMaisRecente implements Iterator<Produto> {

        private No cursor = maisRecente;

        @Override
        public boolean hasNext() {
            return cursor != null;
        }

        @Override
        public Produto next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Historico ja percorrido");
            }
            Produto produto = cursor.produto;
            cursor = cursor.anterior;
            return produto;
        }
    }
}
