package lftc.grammar.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Production
{
    List<Symbol> leftHandSide;
    List<Symbol> rightHandSide;

    public Production(List<Symbol> leftHandSide, List<Symbol> rightHandSide)
    {
        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
    }

    public static List<Production> productionsFromLine(String line)
    {
        var sides = line.split("->");

        var leftHandSide = Arrays.stream(sides[0].trim().split(" "))
            .map(Symbol::new)
            .collect(Collectors.toList());
        var rightHandSide = sides[1].trim().split("\\|");

        List<Production> productions = new ArrayList<>();
        for(String production : rightHandSide)
        {
            var rightHandSymbols = Arrays.stream(production.trim().split(" "))
                .map(Symbol::new)
                .collect(Collectors.toList());

            productions.add(new Production(leftHandSide, rightHandSymbols));
        }

        return productions;
    }

    public List<Symbol> getLeftHandSide()
    {
        return leftHandSide;
    }

    public void setLeftHandSide(List<Symbol> leftHandSide)
    {
        this.leftHandSide = leftHandSide;
    }

    public List<Symbol> getRightHandSide()
    {
        return rightHandSide;
    }

    public void setRightHandSide(List<Symbol> rightHandSide)
    {
        this.rightHandSide = rightHandSide;
    }

    @Override
    public String toString()
    {
        return leftHandSide.stream().map(Symbol::getIdentifier).collect(Collectors.joining(" ")) +
            " -> " + rightHandSide.stream().map(Symbol::getIdentifier).collect(Collectors.joining(" "));
    }
}
