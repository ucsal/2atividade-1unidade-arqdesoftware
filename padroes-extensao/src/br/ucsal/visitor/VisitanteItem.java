package br.ucsal.visitor;

// Um metodo por tipo concreto. Operacao nova = nova implementacao desta interface.
public interface VisitanteItem<R> {

    R visitarLivro(Livro livro);

    R visitarEletronico(Eletronico eletronico);

    R visitarMedicamento(Medicamento medicamento);
}
