package lftc.scanner;

import java.util.Objects;

public class Token
{
    private final TokenType tokenType;

    private final String value;

    private final Integer line, col;

    public Token(TokenType tokenType, String value, Integer line, Integer col)
    {
        this.tokenType = tokenType;
        this.value = value;
        this.line = line;
        this.col = col;
    }

    public TokenType getTokenType()
    {
        return tokenType;
    }

    public String getValue()
    {
        return value;
    }

    public Integer getLine()
    {
        return line;
    }

    public Integer getCol()
    {
        return col;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Token token = (Token) o;
        return tokenType == token.tokenType && Objects.equals(value, token.value);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(tokenType, value);
    }

    @Override
    public String toString()
    {
        return "Token{" +
            "tokenType=" + tokenType +
            ", value='" + value + '\'' +
            ", line=" + line +
            ", col=" + col +
            '}';
    }
}
