package linter.type_analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table<T extends TableElement<T>> implements TableElement<T> {
    Map<String, TableElement<T>> elementMap = new HashMap<String, TableElement<T>>();

    public void addElement(List<String> identifier, T element){
        if(identifier.size() > 1){
            String className = identifier.remove(0);
            TableElement<T> classSpace = elementMap.get(className);
            if(classSpace == null)
                throw new RuntimeException("Undefined class"); //TODO: Should be printed by ErrorHandler
            classSpace.addElement(identifier, element);
        }
        else
            elementMap.put(identifier.get(0), element);
    }

    public T getElement(List<String> identifier){
        if(identifier.size() == 0)
            throw new RuntimeException("Cannot process identifier");
        TableElement<T> element;
        element = elementMap.get(identifier.get(0));
        if(element == null)
            return null;
        else {
            if(identifier.size() < 2)
                return element.getElement(new ArrayList<String>()); //pass empty list
            else
                return element.getElement(identifier.subList(1, identifier.size()));
        }
    }

    public void addClassSpace(String identifier){
        elementMap.put(identifier, new Table<T>());
        //TODO: Should be recursive
    }
    
}