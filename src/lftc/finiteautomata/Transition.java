package lftc.finiteautomata;

public class Transition
{
    String fromState, toState;
    String by;

    public Transition(String fromState, String toState, String by)
    {
        this.fromState = fromState;
        this.toState = toState;
        this.by = by;
    }

    @Override
    public String toString()
    {
        return fromState +
            "->" + by +
            "->" + toState;
    }
}
