package lftc.scanner;

import java.io.IOException;
import java.util.List;

import lftc.finiteautomata.FiniteAutomata;
import lftc.finiteautomata.IdentifierFiniteAutomata;

public class Scanner
{
    private int currentPosition = 0;
    private int startPositionOfNewToken = 0;
    private int lastNewLinePosition = 0;
    private int currentLineNumber = 0;

    private String inputString;

    private final List<String> reservedKeywords;

    private final FiniteAutomata identifierFiniteAutomata;

    private final FiniteAutomata numericalConstantFiniteAutomata;

    public Scanner(String inputString, List<String> reservedKeywords) throws IOException
    {
        this.inputString = inputString;
        this.reservedKeywords = reservedKeywords;

        identifierFiniteAutomata = new IdentifierFiniteAutomata();
        numericalConstantFiniteAutomata = FiniteAutomata.fromFile("numberFA.txt");
    }

    public Token getNextToken()
    {
        if(currentPosition <= inputString.length())
        {
            if(currentPosition == inputString.length() || isWhitespace())
            {
                if(startPositionOfNewToken != currentPosition)
                {
                    Token token = processNewToken();

                    currentPosition++;
                    startPositionOfNewToken = currentPosition;

                    return token;
                }
                else
                {
                    startPositionOfNewToken++;
                }
            }

            currentPosition++;

            return getNextToken();
        }
        else
        {
            return new Token(TokenType.EOF, null, null, null);
        }
    }

    private Token processNewToken()
    {
        String newToken = inputString.substring(startPositionOfNewToken, currentPosition);

        TokenType tokenType = null;

        if(reservedKeywords.contains(newToken))
        {
            tokenType = TokenType.KEYWORD;
        }
        else if(identifierFiniteAutomata.checkSequence(newToken))
        {
            tokenType = TokenType.IDENTIFIER;
            newToken = "IDENTIFIER";
            //TODO add to Symbol Table
        }
        else if(numericalConstantFiniteAutomata.checkSequence(newToken))
        {
            tokenType = TokenType.NUMERIC_CONSTANT;
            newToken = "CONSTANT";
            //TODO add to Symbol Table
        }
        else
        {
            throw new LexicalError(newToken, currentLineNumber, startPositionOfNewToken - lastNewLinePosition);
        }

        return new Token(tokenType, newToken, currentLineNumber, startPositionOfNewToken - lastNewLinePosition);
    }
    private boolean isWhitespace()
    {
        if(inputString.charAt(currentPosition) == '\n')
        {
            lastNewLinePosition = currentPosition;
            currentLineNumber++;
        }
        return List.of(' ','\n').contains(inputString.charAt(currentPosition));
    }
}
