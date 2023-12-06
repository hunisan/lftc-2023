package lftc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lftc.grammar.Grammar;
import lftc.parser.RecursiveDescentParser;
import lftc.scanner.Scanner;
import lftc.scanner.Token;
import lftc.scanner.TokenType;
import lftc.ui.FiniteAutomataMenu;
import lftc.ui.ParserMenu;

public class Main
{

    private static void scannerTest() throws IOException
    {
        //read Token.in
        List<String> reservedKeywords = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Token.in")))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                reservedKeywords.add(line);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        var scanner = new Scanner("IDENTIFIER < 123 > \n for while", reservedKeywords);

        Token token = scanner.getNextToken();
        while (token.getTokenType() != TokenType.EOF)
        {
            token = scanner.getNextToken();
        }
    }

    private static String readFromFile(String fileName)
    {
        String result = "";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                result = result.concat(line);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return result;
    }

    private static List<String> readLinesFromFile(String fileName)
    {
        List<String> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                result.add(line);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return result;
    }


    private static void parserTest() throws IOException
    {
        List<String> reservedKeywords =
            readLinesFromFile("SimpleToken.in");

        String inputString = readFromFile("seq.txt");
        var scanner = new Scanner(inputString, reservedKeywords);
        var grammar = Grammar.fromLines(readLinesFromFile("g1.txt"));
        var parser = new RecursiveDescentParser(grammar, scanner);

        parser.parse();
    }

    public static void main(String[] args) throws IOException
    {
        //scannerTest();
        parserTest();
        //new FiniteAutomataMenu().run();

        var menu = new ParserMenu();

        //menu.run();
    }
}
