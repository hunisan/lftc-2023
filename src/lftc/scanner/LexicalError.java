package lftc.scanner;

public class LexicalError extends RuntimeException
{
    LexicalError(String token, int line, int col)
    {
        super(String.format("Lexical error! Invalid token %s at line %s col %s!",token,line,col));
    }
}
