package lftc.grammar.model;

import java.util.Objects;

public class Symbol
{
    public String identifier;

    public String getIdentifier()
    {
        return identifier;
    }

    public void setIdentifier(String identifier)
    {
        this.identifier = identifier;
    }

    public Symbol(String identifier)
    {
        this.identifier = identifier;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Symbol symbol = (Symbol) o;
        return Objects.equals(identifier, symbol.identifier);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(identifier);
    }
}
