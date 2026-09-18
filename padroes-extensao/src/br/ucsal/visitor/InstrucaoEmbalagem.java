package br.ucsal.visitor;

// Segunda operacao, devolvendo String em vez de BigDecimal.
// Foi escrita sem alterar Livro, Eletronico ou Medicamento.
public class InstrucaoEmbalagem implements VisitanteItem<String> {

    @Override
    public String visitarLivro(Livro livro) {
        return "Envelope de papelao; proteger da umidade. ISBN " + livro.getIsbn();
    }

    @Override
    public String visitarEletronico(Eletronico eletronico) {
        return eletronico.isFragil()
                ? "Caixa reforcada, plastico-bolha e selo FRAGIL."
                : "Caixa comum.";
    }

    @Override
    public String visitarMedicamento(Medicamento medicamento) {
        return medicamento.isControlado()
                ? "Entrega ao titular, com retencao da receita."
                : "Entrega convencional.";
    }
}
