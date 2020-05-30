package linter.type_analysis;

import java.util.ArrayList;
import java.util.List;

public class Variable implements TableElement<Variable> {
    Type type = null;
    List<String> identifier;
    int numberOfReferences = 0;

    public Variable(List<String> identifier){
        this.identifier = identifier;
    }

    public Variable(String identifier){
        List<String> identifierList = new ArrayList<String>();
        identifierList.add(identifier);
        this.identifier = identifierList;
    }

    @Override
    public Variable getElement(List<String> identifier) throws RuntimeException {
        return this;
    }

    @Override
    public void addElement(List<String> subList, Variable element) {
        throw new RuntimeException("Cannot add variable to variable"); //Programmer side error
    }

    public Type getType(){
        return type;
    }

    public void setType(Type type){
        this.type = type;
    }

    public void incrementNumberOfReferences(){
        numberOfReferences++;
    }

    public int getNumberOfReferences(){
        return numberOfReferences;
    }

    public List<String> getIdentifier(){
        return identifier;
    }

    public boolean compareIdentifier(List<String> identifier){
        if(identifier.size() != this.identifier.size())
            return false;
        int index = 0;
        String identifierPartToCompare;
        for(String identifierPart : this.identifier){
            identifierPartToCompare = identifier.get(index++);
            if(!identifierPart.equals(identifierPartToCompare))
                return false;
        }
        return true;
    }
}