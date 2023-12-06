package lftc.symboltable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashSymbolTable implements SymbolTable
{
    private List<String> indexes = new ArrayList<>();
    private int nextIndex = 0;
    //TODO replace with own implementation
    private HashMap<String, Integer> hashTable = new HashMap<>();

    @Override
    public int add(String identifier)
    {
        if(hashTable.containsKey(identifier))
        {
            return hashTable.get(identifier);
        }

        indexes.add(identifier);
        hashTable.put(identifier, this.nextIndex);

        nextIndex++;
        return nextIndex - 1;
    }


    @Override
    public String get(int id)
    {
        return this.indexes.get(id);
    }
}
