# Atividade Pontuada da 1ª Unidade

Este projeto apresenta a implementação e explicação dos padrões de projeto de Extensão: Decorator, Iterator e Visitor.

Aluno: Yuri Silva Souza · Matéria: Arquitetura de Software

## 1. Decorator

O padrão Decorator permite adicionar novos comportamentos a um objeto dinamicamente, colocando-os dentro de wrappers que contêm esses comportamentos.

Imaginemos um gateway de pagamentos onde uma compra pode receber juros de parcelamento, seguro de entrega e cupom de desconto. Cada cliente escolhe uma combinação diferente no checkout. Sem o padrão, seria preciso uma subclasse para cada combinação possível — com três encargos, oito classes.

### Implementação:

```java
// Interface Base
public interface Cobranca {
    BigDecimal getValor();
    String getDescricao();
}
 
// Componente Concreto
public class PagamentoCartao implements Cobranca {
    public BigDecimal getValor() { return valor; }
    public String getDescricao() { return "Pedido " + pedido + " no cartao " + bandeira; }
}
 
// Decorator Base
public abstract class CobrancaDecorator implements Cobranca {
    protected final Cobranca cobrancaEnvolvida;
    protected CobrancaDecorator(Cobranca c) { this.cobrancaEnvolvida = c; }
}
 
// Decorators Concretos
public class TaxaParcelamento extends CobrancaDecorator {
    public BigDecimal getValor() {
        BigDecimal base = cobrancaEnvolvida.getValor();
        BigDecimal juros = JUROS_MENSAL.multiply(BigDecimal.valueOf(parcelas)).multiply(base);
        return base.add(juros).setScale(2, RoundingMode.HALF_UP);
    }
}
 
public class CupomDesconto extends CobrancaDecorator {
    public BigDecimal getValor() {
        return cobrancaEnvolvida.getValor().subtract(desconto);
    }
}
```

### Uso:

```java
    // PagamentoCartao é criado e o cupom, os juros e o seguro
    // são anexados a ele, um envolvendo o outro
    Cobranca completa =
            new SeguroEntrega(
                new TaxaParcelamento(
                    new CupomDesconto(
                        new PagamentoCartao("1002", "VISA", new BigDecimal("1800.00")),
                        "VOLTA200", new BigDecimal("200.00")),
                    6));
 
    System.out.println(completa.getDescricao());
    System.out.printf("  Total: R$ %,.2f%n", completa.getValor());
```

```
    Pedido 1002 no cartao VISA - cupom VOLTA200 + juros em 6x + seguro
      Total: R$ 1.866,63
```

A ordem em que os decoradores são empilhados faz parte da regra de negócio. Aplicando o mesmo cupom depois dos juros, o total muda:

```
    Pedido 1003 no cartao VISA + juros em 6x + seguro - cupom VOLTA200
      Total: R$ 1.899,95
```

## 2. Iterator

O padrão Iterator fornece uma maneira de acessar sequencialmente os elementos de uma lista sem expor sua representação subjacente.

Um exemplo bom é um carrinho de compras, que armazena diversos produtos. O Iterator permite que o sistema percorra todos os itens para exibir um relatório ou calcular o total, independentemente de como esses produtos estão armazenados internamente. Neste projeto o carrinho guarda os produtos em um array e o histórico de compras em uma lista ligada — estruturas sem nada em comum por dentro, percorridas pelo mesmo código.

### Implementação:

```java
// Classe Agregada
public class CarrinhoDeCompras implements Iterable<Produto> {
    private Produto[] itens = new Produto[4];
    private int quantidade = 0;
 
    public Iterator<Produto> iterator() {
        return new IteradorDoCarrinho();
    }
 
    // Classe interna: só ela conhece o array e o contador
    private class IteradorDoCarrinho implements Iterator<Produto> {
        private int posicao = 0;
        public boolean hasNext() { return posicao < quantidade; }
        public Produto next() {
            if (!hasNext()) throw new NoSuchElementException("Carrinho ja percorrido");
            return itens[posicao++];
        }
    }
}
```

### Uso:

```java
    // O método recebe Iterable, então percorre tanto o carrinho (array)
    // quanto o histórico (lista ligada), sem saber a diferença
    private static void listar(Iterable<Produto> colecao) {
        for (Produto produto : colecao) {
            System.out.printf("  %-24s R$ %,9.2f%n", produto.getNome(), produto.getPreco());
        }
    }
 
    listar(carrinho);
    listar(historico);
```

```
    Carrinho (array por dentro):
      Teclado mecanico         R$    389,90
      Monitor 27 polegadas     R$  1.499,00
      Mouse sem fio            R$    129,90
      SUBTOTAL                 R$  2.018,80
 
    Historico (lista ligada por dentro):
      HD externo 2TB           R$    459,00
      Webcam Full HD           R$    219,90
      Cadeira ergonomica       R$  1.290,00
      SUBTOTAL                 R$  1.968,90
```

Como a posição fica guardada no iterador e não na coleção, dois percursos podem correr ao mesmo tempo sem interferir um no outro:

```
    percursoA (avancou 2x)   -> Mouse sem fio
    percursoB (recem-criado) -> Teclado mecanico
```

## 3. Visitor

O padrão Visitor permite separar um algoritmo da estrutura de objetos sobre a qual ele opera. Ele permite adicionar novas operações a estruturas de objetos complexas sem modificar as próprias classes dessas estruturas.

Em uma nota fiscal temos diferentes tipos de itens (Livro, Eletrônico, Medicamento). Cada tipo tem uma alíquota diferente e uma instrução de embalagem diferente. Usando o Visitor, criamos uma "Calculadora de Imposto" e uma "Instrução de Embalagem" que visitam cada item e aplicam a regra correta, sem poluir as classes de item com lógica fiscal ou logística.

### Implementação:

```java
// Interface Visitor
public interface VisitanteItem<R> {
    R visitarLivro(Livro livro);
    R visitarEletronico(Eletronico eletronico);
    R visitarMedicamento(Medicamento medicamento);
}
 
// Interface do Elemento
public interface ItemNota {
    <R> R aceitar(VisitanteItem<R> visitante);
}
 
// Elemento Concreto — despacho duplo: a JVM escolhe este aceitar() por ser
// um Livro, e visitarLivro(this) escolhe o método certo do visitante
public class Livro implements ItemNota {
    public <R> R aceitar(VisitanteItem<R> visitante) {
        return visitante.visitarLivro(this);
    }
}
 
// Visitante Concreto
public class CalculadoraImposto implements VisitanteItem<BigDecimal> {
    public BigDecimal visitarLivro(Livro l) { return BigDecimal.ZERO.setScale(2); }
    public BigDecimal visitarEletronico(Eletronico e) {
        return arredondar(e.getValor().multiply(new BigDecimal("0.25")));
    }
    public BigDecimal visitarMedicamento(Medicamento m) {
        BigDecimal aliquota = m.isControlado() ? new BigDecimal("0.12") : new BigDecimal("0.08");
        return arredondar(m.getValor().multiply(aliquota));
    }
}
```

O parâmetro genérico `<R>` permite que cada operação devolva o tipo que lhe convém: `CalculadoraImposto` devolve `BigDecimal` e `InstrucaoEmbalagem` devolve `String`.

### Uso:

```java
    // Os itens usam o aceitar() para acessar a implementação de cada visitante
    CalculadoraImposto imposto = new CalculadoraImposto();
    InstrucaoEmbalagem embalagem = new InstrucaoEmbalagem();
 
    for (ItemNota item : nota) {
        System.out.printf("%-24s imposto R$ %,8.2f%n",
                item.getDescricao(), item.aceitar(imposto));
        System.out.println("    " + item.aceitar(embalagem));
    }
```

```
    Padroes de Projeto       valor R$    298,00   imposto R$     0,00
        Envelope de papelao; proteger da umidade. ISBN 978-8573076103
    Notebook Inspiron 15     valor R$  4.200,00   imposto R$ 1.050,00
        Caixa reforcada, plastico-bolha e selo FRAGIL.
    Clonazepam 2mg           valor R$     32,50   imposto R$     3,90
        Entrega ao titular, com retencao da receita.
 
    Total de tributos: R$ 1.152,89
```

Duas operações completamente diferentes rodaram sobre os mesmos objetos, sem nenhum `instanceof` e sem alterar as classes de item.

## Como Executar

O projeto não usa dependências externas. Para executar:

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
 
java -cp out br.ucsal.decorator.MainDecorator
java -cp out br.ucsal.iterator.MainIterator
java -cp out br.ucsal.visitor.MainVisitor
```
