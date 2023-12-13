package lftc.parser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lftc.grammar.Grammar;
import lftc.grammar.model.Production;
import lftc.grammar.model.Symbol;
import lftc.scanner.LexicalError;
import lftc.scanner.Scanner;
import lftc.scanner.Token;
import lftc.scanner.TokenType;

public class RecursiveDescentParser implements Parser
{
    private final Grammar grammar;
    private final Scanner scanner;
    private final String outputFile;

    public RecursiveDescentParser(Grammar grammar, Scanner scanner, String outputFile)
    {
        this.grammar = grammar;
        this.scanner = scanner;
        this.outputFile = outputFile;
    }

    @Override
    public void parse()
    {
        Token token = scanner.getNextToken();
        List<Token> tokens = new ArrayList<>();

        try
        {
            while(token.getTokenType() != TokenType.EOF)
            {
                tokens.add(token);
                token = scanner.getNextToken();
            }

            List<Symbol> symbols = tokens.stream()
                .map(Token::getValue)
                .map(Symbol::new)
                .collect(Collectors.toList());

            parse(symbols);
        }
        catch(LexicalError lexicalError)
        {
            System.out.println(lexicalError.getMessage());
        }
    }

    public void parse(List<Symbol> inputSequence)
    {
        int n = inputSequence.size();
        ParserConfiguration config = new ParserConfiguration(grammar.getStartingSymbol());
        while (config.s != ParsingState.FINAL && config.s != ParsingState.ERROR)
        {
            System.out.println("alfa:"+config.alfa.stream().map(Gamma::toString).collect(Collectors.joining(",")));
            System.out.println("beta:"+config.beta.stream().map(Gamma::toString).collect(Collectors.joining(",")));
            System.out.println("i :"+config.i);
            System.out.println("s :"+config.s);
            System.out.println("");

            if (config.s == ParsingState.NORMAL)
            {
                if (config.i == n && config.beta.isEmpty())
                {
                    success(config);
                }
                else if(config.beta.isEmpty())
                {
                    momentaryInsuccess(config);
                }
                else if (grammar.isNonTerminal(config.beta.peek().symbol)) //Head(beta) = A
                {
                    expand(config);
                }
                else if (config.i < n && Objects.equals(config.beta.peek().symbol,inputSequence.get(config.i))) //Head(beta) = ai
                {
                    advance(config);
                }
                else
                {
                    momentaryInsuccess(config);
                }
            }
            else if (config.s == ParsingState.BACK)
            {
                if (grammar.isTerminal(config.alfa.peek().symbol)) //Head(alfa) = a
                {
                    back(config);
                }
                else
                {
                    anotherTry(config);
                }
            }
        }


        if(config.s == ParsingState.ERROR)
        {
            System.out.println("Syntax error! Unexpected token " + inputSequence.get(config.maxi));
            //TODO add line and col information
        }
        else
        {
            System.out.println("Syntax correct!");

            writeOutputFile(config);
        }
    }

    private void writeOutputFile(ParserConfiguration config)
    {
        if(outputFile == null)
        {
            return;
        }

        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

            /*writer.write(config.alfa.stream()
                .filter(g -> grammar.isNonTerminal(g.symbol))
                .map(Gamma::toString)
                .collect(Collectors.joining(",")));*/

            for(Gamma g : config.alfa)
            {
                if(grammar.isNonTerminal(g.symbol))
                {
                    writer.write(g.symbol + "(" + g.productionIndex + ")\n");
                }
                else
                {
                    writer.write(g.symbol + "\n");
                }
            }

            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void success(ParserConfiguration config)
    {
        config.s = ParsingState.FINAL;
    }

    public void expand(ParserConfiguration config)
    {
        Gamma g = config.beta.pop();
        Symbol nonTerminal = g.symbol;
        var productions = grammar.getProductionsOfNonTerminal(nonTerminal);
        var production = productions.get(0);

        config.alfa.push(new Gamma(nonTerminal, 0));

        for(int i = production.getRightHandSide().size() - 1 ; i >= 0; i--)
        {
            config.beta.push(new Gamma(production.getRightHandSide().get(i), 0));
        }
    }

    public void advance(ParserConfiguration config)
    {
        //System.out.println("Advance!");
        Gamma g = config.beta.pop();
        Symbol terminal = g.symbol;
        config.i++;
        if(config.i > config.maxi)
        {
            config.maxi = config.i;
        }
        config.alfa.push(new Gamma(terminal, 0));
    }

    public void momentaryInsuccess(ParserConfiguration config)
    {
        config.s = ParsingState.BACK;
    }

    public void back(ParserConfiguration config)
    {
        Gamma g = config.alfa.pop();
        config.i--;
        config.beta.push(g);
    }

    public void anotherTry(ParserConfiguration config)
    {
        Gamma headOfWorkingStack = config.alfa.peek();

        Symbol nonTerminal = headOfWorkingStack.symbol;

        List<Production> productions = grammar.getProductionsOfNonTerminal(headOfWorkingStack.symbol);

        if (headOfWorkingStack.productionIndex + 1 < productions.size())
        {
            var production = productions.get(headOfWorkingStack.productionIndex);
            for(int i = 0; i < production.getRightHandSide().size(); i++)
            {
                config.beta.pop();
            }
            headOfWorkingStack.productionIndex++;
            var newProduction = productions.get(headOfWorkingStack.productionIndex);

            for(int i = newProduction.getRightHandSide().size() - 1 ; i >= 0; i--)
            {
                config.beta.push(new Gamma(newProduction.getRightHandSide().get(i), 0));
            }


            config.s = ParsingState.NORMAL;
        }
        else
        {
            if (config.i == 0
                && headOfWorkingStack.symbol == grammar.getStartingSymbol())
            {
                config.s = ParsingState.ERROR;
            }
            else
            {
                var production = productions.get(headOfWorkingStack.productionIndex);
                config.alfa.pop();
                for(int i = 0; i < production.getRightHandSide().size(); i++)
                {
                    config.beta.pop();
                }
                config.beta.push(new Gamma(nonTerminal, 0));
            }
        }
    }
}
