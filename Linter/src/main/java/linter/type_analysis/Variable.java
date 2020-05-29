package linter.type_analysis;

import java.util.List;

public class Variable implements TableElement<Variable> {
    Type type = null;
    List<String> identifier;
    int numberOfReferences = 0;

    public Variable(List<String> identifier){
        this.identifier = identifier;
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
}