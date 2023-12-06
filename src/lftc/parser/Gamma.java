package lftc.parser;

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
}
