package lftc.ui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lftc.finiteautomata.FiniteAutomata;
import lftc.finiteautomata.Transition;
import lftc.grammar.Grammar;
import lftc.grammar.model.Production;
import lftc.grammar.model.Symbol;

public class FiniteAutomataMenu
{
    private FiniteAutomata finiteAutomata;

    BufferedReader reader = new BufferedReader(
        new InputStreamReader(System.in));


    public void run() throws IOException
    {
        finiteAutomata = FiniteAutomata.fromFile("fa.txt");
        System.out.println("Welcome");

        String input = "";
        while (!Objects.equals(input, "exit"))
        {
            System.out.print("Commands: \n s - display states\n"
                + " a - display alphabet\n"
                + " t - display transitions\n"
                + " i - initial state\n"
                + " f - final states\n"
                + " c - check a sequence\n"
                + " exit - Exit the program\nCommand:");
            input = reader.readLine();
            switch (input)
            {
                case "s":
                    printStates();
                    break;
                case "a":
                    printAlphabet();
                    break;
                case "t":
                    printTransitions();
                    break;
                case "i":
                    printInitialState();
                    break;
                case "f":
                    printFinalState();
                    break;
                case "c":
                    checkSequence();
                    break;
                default:
                    System.out.println("Unrecognized command!");
            }
        }
    }

    private void checkSequence() throws IOException
    {
        System.out.println("Enter a sequence:");
        var sequence = reader.readLine();

        if(finiteAutomata.checkSequence(sequence))
        {
            System.out.println("The sequence is accepted by the fa!");
        }
        else
        {
            System.out.println("The sequence is not accepted by the fa!");
        }

    }
    private void printFinalState()
    {
        System.out.println("Final state: ");
        System.out.println(finiteAutomata.getFinalStates().stream().collect(Collectors.joining(",")));
    }

    private void printInitialState()
    {
        System.out.println("Initial state: ");
        System.out.println(finiteAutomata.getInitialState());
    }

    private void printTransitions()
    {
        System.out.println("Transitions: ");
        System.out.println(
            finiteAutomata.getTransitions().stream().map(Transition::toString).collect(Collectors.joining("\n")));
    }

    private void printAlphabet()
    {
        System.out.println("Alphabet: ");
        System.out.println(finiteAutomata.getAlphabet().stream().collect(Collectors.joining(",")));
    }

    private void printStates()
    {
        System.out.println("States: ");
        System.out.println(finiteAutomata.getStates().stream().collect(Collectors.joining(",")));
    }

}
