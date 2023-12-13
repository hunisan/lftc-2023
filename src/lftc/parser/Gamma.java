package lftc.parser;

import java.util.Objects;

import lftc.grammar.model.Symbol;

public class Gamma
{
    public Symbol symbol;
    public Integer productionIndex;

    public Gamma(Symbol nonTerminal, Integer productionIndex)
    {
        this.symbol = nonTerminal;
        this.productionIndex = productionIndex;
    }

    @Override
    public String toString()
    {
        return "G{" + symbol +
            ", " + productionIndex +
            '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Gamma gamma = (Gamma) o;
        return Objects.equals(symbol, gamma.symbol) && Objects.equals(productionIndex,
            gamma.productionIndex);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(symbol, productionIndex);
    }
}
