package linter.type_analysis;

import java.util.List;

public class Variable implements TableElement<Variable> {
    Type type = null;
    List<String> identifier;

    public Variable(List<String> identifier){
        this.identifier = identifier;
    }

    @Override
    public Variable getElement(List<String> identifier) throws RuntimeException {
        if(this.identifier.get(identifier.size()-1).equals(identifier.get(0)))
            return this;
        throw new RuntimeException("Bad identifier"); //Programmer side error
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

    public List<String> getIdentifier(){
        return identifier;
    }
}