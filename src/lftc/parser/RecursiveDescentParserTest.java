package lftc.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import lftc.grammar.Grammar;
import lftc.grammar.model.Production;
import lftc.grammar.model.Symbol;

public class RecursiveDescentParserTest
{
    private RecursiveDescentParser recursiveDescentParser;

    private Grammar grammar;

    @Before
    public void before()
    {
        Set<Symbol> terminals = new HashSet<>();
        terminals.add(new Symbol("a"));
        terminals.add(new Symbol("b"));
        terminals.add(new Symbol("c"));
        Set<Symbol> nonTerminals = new HashSet<>();
        nonTerminals.add(new Symbol("A"));
        List<Production> productions = new ArrayList<>();
        productions.add(new Production(List.of(new Symbol("A")),
            List.of(new Symbol("a"), new Symbol("b"))));
        productions.add(new Production(List.of(new Symbol("A")), List.of(new Symbol("c"))));
        // Productions:
        // A -> a b
        // A -> c

        grammar = new Grammar(productions, terminals, nonTerminals);
        recursiveDescentParser = new RecursiveDescentParser(grammar, null);
    }
    @Test
    public void testAdvance()
    {
        //given
        Symbol s = new Symbol("s");
        Gamma g = new Gamma(s, 0);

        Stack<Gamma> alfa = new Stack<>();
        Stack<Gamma> beta = new Stack<>();
        beta.push(g);
        ParserConfiguration config = new ParserConfiguration(ParsingState.NORMAL, 0, alfa, beta);

        //when
        recursiveDescentParser.advance(config);

        //then
        Assert.assertEquals(g, config.alfa.peek());
        Assert.assertTrue(beta.isEmpty());
    }

    @Test
    public void testBack()
    {
        //given
        Symbol s = new Symbol("s");
        Gamma g = new Gamma(s, 0);

        Stack<Gamma> alfa = new Stack<>();
        alfa.push(g);
        Stack<Gamma> beta = new Stack<>();
        ParserConfiguration config = new ParserConfiguration(ParsingState.BACK, 1, alfa, beta);

        //when
        recursiveDescentParser.back(config);

        //then
        Assert.assertEquals(ParsingState.BACK, config.s);
        Assert.assertEquals(0, config.i);
        Assert.assertEquals(g, config.beta.peek());
        Assert.assertTrue(config.alfa.isEmpty());
    }

    @Test
    public void testMomentaryInsuccess()
    {
        //given
        Stack<Gamma> alfa = new Stack<>();
        Stack<Gamma> beta = new Stack<>();
        ParserConfiguration config = new ParserConfiguration(ParsingState.NORMAL, 0, alfa, beta);

        //when
        recursiveDescentParser.momentaryInsuccess(config);

        //then
        Assert.assertEquals(ParsingState.BACK, config.s);
    }

    @Test
    public void testExpand()
    {
        Symbol s = new Symbol("A");
        Gamma g = new Gamma(s, 0);

        Stack<Gamma> alfa = new Stack<>();
        Stack<Gamma> beta = new Stack<>();
        beta.push(g);
        ParserConfiguration config = new ParserConfiguration(ParsingState.NORMAL, 1, alfa, beta);

        //when
        recursiveDescentParser.expand(config);

        Assert.assertEquals(ParsingState.NORMAL, config.s);
        Assert.assertEquals(1, config.alfa.size());
        Assert.assertEquals(new Gamma(new Symbol("A"), 0), config.alfa.peek());
        Assert.assertEquals(2, config.beta.size());
        Assert.assertEquals(new Gamma(new Symbol("a"), 0), config.beta.pop());
        Assert.assertEquals(new Gamma(new Symbol("b"), 0), config.beta.pop());
    }

    @Test
    public void testAnotherTry()
    {
        Symbol s = new Symbol("A");
        Gamma g = new Gamma(s, 0);

        Stack<Gamma> alfa = new Stack<>();
        Stack<Gamma> beta = new Stack<>();
        alfa.push(g);
        beta.push(new Gamma(new Symbol("b"), 0));
        beta.push(new Gamma(new Symbol("a"), 0));
        ParserConfiguration config = new ParserConfiguration(ParsingState.BACK, 1, alfa, beta);

        //when
        recursiveDescentParser.anotherTry(config);

        Assert.assertEquals(ParsingState.NORMAL, config.s);
        Assert.assertEquals(1, config.alfa.size());
        Assert.assertEquals(new Gamma(new Symbol("A"), 1), config.alfa.peek());
        Assert.assertEquals(1, config.beta.size());
        Assert.assertEquals(new Gamma(new Symbol("c"), 0), config.beta.peek());
    }
}


