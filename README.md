# Atividade Pontuada da 1ª Unidade

Este projeto apresenta a implementação e explicação dos padrões de projeto de Extensão: Decorator, Iterator e Visitor.

Aluno: Yuri Silva Souza · Matéria: Arquitetura de Software

| Padrão | Mini-projeto | Problema que resolve |
|---|---|---|
| Decorator | Gateway de pagamentos | encargos opcionais que se combinam de várias formas |
| Iterator | Carrinho e histórico de compras | percorrer coleções de estruturas internas diferentes |
| Visitor | Nota fiscal | operações que crescem sobre tipos que não mudam |
 
## Executar
 
```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
 
java -cp out br.ucsal.decorator.MainDecorator
java -cp out br.ucsal.iterator.MainIterator
java -cp out br.ucsal.visitor.MainVisitor
```
 
No PowerShell, a primeira linha é:
 
```powershell
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse -Filter *.java src | % FullName)
```
 
Precisa de JDK 8 ou superior. Sem dependências externas.
 
## Decorator 
 
Gateway de pagamentos. Adiciona encargos a uma cobrança em tempo de execução, envolvendo um objeto dentro do outro.
 
Funciona porque `CobrancaDecorator` implementa `Cobranca` e ao mesmo tempo contém uma `Cobranca`. Cada decorador pede o valor a quem está abaixo e acrescenta o seu. Como o decorador também é uma `Cobranca`, outro decorador pode envolvê-lo.
 
```java
Cobranca c =
        new SeguroEntrega(
            new TaxaParcelamento(
                new CupomDesconto(
                    new PagamentoCartao("1002", "VISA", new BigDecimal("1800.00")),
                    "VOLTA200", new BigDecimal("200.00")),
                6));
 
c.getValor();
```
 
A ordem importa: o cupom antes dos juros dá R$ 1.866,63; depois dos juros, R$ 1.899,95.
  
## Iterator 
 
Carrinho e histórico de compras. Percorre uma coleção sem que ninguém precise conhecer sua estrutura interna.
 
O carrinho guarda os produtos em um array; o histórico, em uma lista ligada percorrida do mais recente ao mais antigo. Os dois implementam `Iterable<Produto>`, então o mesmo método serve para ambos:
 
```java
private static void listar(Iterable<Produto> colecao) {
    for (Produto produto : colecao) {
        ...
    }
}
 
listar(carrinho);
listar(historico);
```
 
Como a posição fica guardada no iterador e não na coleção, dois percursos podem correr ao mesmo tempo sem interferir um no outro.
 
## Visitor
 
Nota fiscal. Adiciona operações sobre livros, eletrônicos e medicamentos sem alterar essas classes.
 
Cada item tem um método `aceitar()` que encaminha a chamada ao método certo do visitante. São duas chamadas polimórficas em sequência (despacho duplo), o que dispensa a cadeia de `instanceof`:
 
```java
public class Livro implements ItemNota {
    public <R> R aceitar(VisitanteItem<R> visitante) {
        return visitante.visitarLivro(this);
    }
}
```
 
Há dois visitantes: `CalculadoraImposto` devolve `BigDecimal` e `InstrucaoEmbalagem` devolve `String`. O genérico `<R>` é o que permite retornos de tipos diferentes.
 
```java
for (ItemNota item : nota) {
    item.aceitar(imposto);
    item.aceitar(embalagem);
}
```
