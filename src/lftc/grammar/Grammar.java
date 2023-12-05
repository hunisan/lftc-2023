package lftc.grammar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lftc.grammar.model.Production;
import lftc.grammar.model.Symbol;

public class Grammar
{
    private List<Production> productions;
    private Set<Symbol> terminals;
    private Set<Symbol> nonTerminals;

    public Grammar(List<Production> productions, Set<Symbol> terminals,
        Set<Symbol> nonTerminals)
    {
        this.productions = productions;
        this.terminals = terminals;
        this.nonTerminals = nonTerminals;
    }

    public static Grammar fromLines(final List<String> lines)
    {
        Set<Symbol> terminals = new HashSet<>();
        Set<Symbol> nonTerminals = new HashSet<>();
        List<Production> productions = new ArrayList<>();

        lines.stream().map(Production::productionsFromLine).forEach(productions::addAll);

        productions.forEach(production -> {
            nonTerminals.addAll(production.getLeftHandSide());
            terminals.removeAll(production.getLeftHandSide());

            production.getRightHandSide().forEach(symbol -> {
                if (!nonTerminals.contains(symbol))
                {
                    terminals.add(symbol);
                }
            });
        });

        return new Grammar(productions, terminals, nonTerminals);
    }

    public boolean isCFG()
    {
        for (Production rule : productions)
        {
            if (rule.getLeftHandSide().size() > 1)
            {
                return false;
            }
        }

        return true;
    }

    public List<Production> getProductions()
    {
        return productions;
    }

    public void setProductions(List<Production> productions)
    {
        this.productions = productions;
    }

    public Set<Symbol> getTerminals()
    {
        return terminals;
    }

    public void setTerminals(Set<Symbol> terminals)
    {
        this.terminals = terminals;
    }

    public Set<Symbol> getNonTerminals()
    {
        return nonTerminals;
    }

    public void setNonTerminals(Set<Symbol> nonTerminals)
    {
        this.nonTerminals = nonTerminals;
    }

    public List<Production> getProductionsOfNonTerminal(String nonTerminal)
    {
        return productions.stream()
            .filter(production -> production.getLeftHandSide().contains(new Symbol(nonTerminal)))
            .collect(Collectors.toList());
    }
}
