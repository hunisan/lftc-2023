package lftc.finiteautomata;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class IdentifierFiniteAutomata extends FiniteAutomata
{
    public IdentifierFiniteAutomata()
    {
        states.add("q0");
        states.add("q1");
        initialState = "q0";
        finalStates.add("q1");

        for(char c = 'a'; c <= 'z'; c++)
        {
            alphabet.add(Character.toString(c));
            transitions.add(new Transition("q0","q1", Character.toString(c)));
            transitions.add(new Transition("q1","q1", Character.toString(c)));
        }
        for(char c = 'A'; c <= 'Z'; c++)
        {
            alphabet.add(Character.toString(c));
            transitions.add(new Transition("q0","q1", Character.toString(c)));
            transitions.add(new Transition("q1","q1", Character.toString(c)));
        }

        for(char c = '0'; c <= '9'; c++)
        {
            alphabet.add(Character.toString(c));
            transitions.add(new Transition("q1","q1", Character.toString(c)));
        }
        alphabet.add("_");
        transitions.add(new Transition("q0","q1", Character.toString('_')));
        transitions.add(new Transition("q1","q1", Character.toString('_')));


    }
}
