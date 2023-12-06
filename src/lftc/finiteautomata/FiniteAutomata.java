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

public class FiniteAutomata
{
    Set<String> states = new HashSet<>();
    Set<String> alphabet = new HashSet<>();
    Set<Transition> transitions = new HashSet<>();
    String initialState;
    Set<String> finalStates = new HashSet<>();

    public static FiniteAutomata fromFile(String fileName) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        List<String> lines = new ArrayList<>();
        while ((line = br.readLine()) != null)
        {
            lines.add(line);
        }

        return FiniteAutomata.fromLines(lines);
    }

    public static FiniteAutomata fromLines(List<String> lines)
    {
        var finiteAutomata = new FiniteAutomata();
        finiteAutomata.states = Arrays.stream(lines.remove(0).split(" ")).collect(Collectors.toSet());

        finiteAutomata.alphabet = Arrays.stream(lines.remove(0).split(" ")).collect(Collectors.toSet());

        int nrOfTransitions = Integer.parseInt(lines.remove(0));
        finiteAutomata.transitions = new HashSet<>();
        for (int i = 0; i < nrOfTransitions; i++)
        {
            var transitionParts = lines.remove(0).split(" ");
            finiteAutomata.transitions.add(new Transition(transitionParts[0], transitionParts[2], transitionParts[1]));
        }

        finiteAutomata.initialState = lines.remove(0);

        finiteAutomata.finalStates = Arrays.stream(lines.remove(0).split(" ")).collect(Collectors.toSet());

        return finiteAutomata;
    }

    public boolean checkSequence(String s)
    {
        String currentState = initialState;

        int index = 0;
        while (index < s.length())
        {
            String nextCharacter = s.substring(index, index + 1);
            String finalCurrentState = currentState;
            var transitionTaken = transitions.stream()
                .filter(transition -> transition.fromState.equals(finalCurrentState) &&
                    transition.by.equals(nextCharacter)).findFirst();

            if (transitionTaken.isPresent())
            {
                currentState = transitionTaken.get().toState;
                index++;
            }
            else
            {
                return false;
            }

        }

        if (!finalStates.contains(currentState))
        {
            return false;
        }

        return true;
    }

    public Set<String> getStates()
    {
        return states;
    }

    public Set<String> getAlphabet()
    {
        return alphabet;
    }

    public Set<Transition> getTransitions()
    {
        return transitions;
    }

    public String getInitialState()
    {
        return initialState;
    }

    public Set<String> getFinalStates()
    {
        return finalStates;
    }
}
