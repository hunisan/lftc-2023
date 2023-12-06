package lftc.ui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lftc.grammar.Grammar;
import lftc.grammar.model.Production;
import lftc.grammar.model.Symbol;

public class ParserMenu
{
    private Grammar grammar;

    BufferedReader reader = new BufferedReader(
        new InputStreamReader(System.in));

    public void run() throws IOException
    {
        System.out.println("Welcome");

        String input = "";
        while (!Objects.equals(input, "exit"))
        {
            System.out.print("Commands: \n r - Read a grammar file\n"
                + " nt - print nonterminals\n"
                + " t - print terminals\n"
                + " p - print productions\n"
                + " pnt - print productions of a non-terminal\n"
                + " cfg - perform CFG check\n"
                + " exit - Exit the program\nCommand:");
            input = reader.readLine();
            switch (input)
            {
                case "r":
                    readGrammar();
                    break;
                case "nt":
                    printNonTerminals();
                    break;
                case "t":
                    printTerminals();
                    break;
                case "p":
                    printProductions();
                    break;
                case "pnt":
                    printProductionsOfNonTerminal();
                    break;
                case "cfg":
                    printCFGCheck();
                    break;
                case "x":
                    break;
                default:
                    System.out.println("Unrecognized command!");
            }
        }
    }

    private void readGrammar() throws IOException
    {
        System.out.println("Enter the name of the file to be read:");
        String fileName = reader.readLine();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            String line;
            List<String> lines = new ArrayList<>();
            while ((line = br.readLine()) != null)
            {
                lines.add(line);
            }

            grammar = Grammar.fromLines(lines);

            System.out.println("Grammar read successfully!");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void printNonTerminals()
    {
        if (grammar != null)
        {
            System.out.println("Non-terminals: ");
            System.out.println(
                grammar.getNonTerminals().stream()
                    .map(Symbol::getIdentifier)
                    .collect(Collectors.joining(",")));
        }
        else
        {
            printNoGrammarMessage();
        }
    }

    private void printTerminals()
    {
        if (grammar != null)
        {
            System.out.println("Terminals: ");
            System.out.println(
                grammar.getTerminals().stream()
                    .map(Symbol::getIdentifier)
                    .collect(Collectors.joining(",")));
        }
        else
        {
            printNoGrammarMessage();
        }
    }

    private void printCFGCheck()
    {
        if (grammar != null)
        {
            if (grammar.isCFG())
            {
                System.out.println("The grammar is context-free!");
            }
            else
            {
                System.out.println("The grammar is NOT context-free!");
            }
        }
        else
        {
            printNoGrammarMessage();
        }
    }

    private void printProductions()
    {
        if (grammar != null)
        {
            System.out.println("Productions: ");
            System.out.println(
                grammar.getProductions().stream()
                    .map(Production::toString)
                    .collect(Collectors.joining("\n")));
        }
        else
        {
            printNoGrammarMessage();
        }
    }

    private void printProductionsOfNonTerminal() throws IOException
    {
        System.out.println("Enter a non-terminal:");
        String input = reader.readLine();

        var productions = grammar.getProductionsOfNonTerminal(input);
        if (productions.isEmpty())
        {
            System.out.println("No such non-terminal exists");
        }
        else
        {
            System.out.println("Production rules of the chosen non-terminal:");
            System.out.println(productions.stream()
                .map(Production::toString)
                .collect(Collectors.joining("\n")));
        }
    }

    private void printNoGrammarMessage()
    {
        System.out.println("You need to read a grammar from a file first");
    }

}
