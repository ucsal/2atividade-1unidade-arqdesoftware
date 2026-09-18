package br.ucsal.decorator;

import java.util.Objects;

// Implementa Cobranca e ao mesmo tempo contem uma Cobranca: e isso que permite empilhar.
public abstract class CobrancaDecorator implements Cobranca {

    protected final Cobranca cobrancaEnvolvida;

    protected CobrancaDecorator(Cobranca cobrancaEnvolvida) {
        this.cobrancaEnvolvida = Objects.requireNonNull(cobrancaEnvolvida);
    }
}
