package lftc.parser;

import java.util.Stack;

import lftc.grammar.model.Symbol;

public class ParserConfiguration
{
    public ParsingState s;
    public int i;
    //alfa/ working stack
    //beta/ input stack
    public Stack<Gamma> alfa;
    public Stack<Gamma> beta; //placeholder

    public ParserConfiguration(Symbol startingSymbol)
    {
        s = ParsingState.NORMAL;
        i = 0;
        alfa = new Stack<>();
        beta = new Stack<>();
        beta.add(new Gamma(startingSymbol, 0));
    }
}
