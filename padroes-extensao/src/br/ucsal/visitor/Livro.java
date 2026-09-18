package br.ucsal.visitor;

import java.math.BigDecimal;

public class Livro implements ItemNota {

    private final String titulo;
    private final String isbn;
    private final BigDecimal valor;

    public Livro(String titulo, String isbn, BigDecimal valor) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.valor = valor;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public String getDescricao() {
        return titulo;
    }

    @Override
    public BigDecimal getValor() {
        return valor;
    }

    // Despacho duplo: a JVM escolhe este aceitar() por ser um Livro,
    // e visitarLivro(this) escolhe o metodo certo do visitante.
    @Override
    public <R> R aceitar(VisitanteItem<R> visitante) {
        return visitante.visitarLivro(this);
    }
}
