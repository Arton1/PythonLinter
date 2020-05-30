package linter.type_analysis;

import java.util.ArrayList;
import java.util.List;

public class Function implements TableElement<Function> {
    List<String> identifier;
    private Type returnType;
    private List<Type> arguments = new ArrayList<Type>();
    int numberOfReferences = 0;

    public Function(List<String> identifier, Type returnType, List<Type> arguments){
        this.identifier = identifier;
        this.returnType = returnType;
        this.arguments = arguments;
    }

    public Function(String identifier, Type returnType, List<Type> arguments){
        List<String> identifierList = new ArrayList<String>();
        identifierList.add(identifier);
        this.identifier = identifierList;
        this.returnType = returnType;
        this.arguments = arguments;
    }

    public List<String> getIdentifier(){
        return identifier;
    }

    public Type getReturnType(){
        return returnType;
    }

    public List<Type> getArguments(){
        return arguments;
    }

    public int getNumberOfReferences(){
        return numberOfReferences;
    }

    public void incrementNumberOfReferences(){
        numberOfReferences++;
    }

    public boolean compareArgumentTypes(List<Type> arguments){
        int index = 0;
        Type argumentToCompare;
        for(Type argument : this.arguments){
            if(index < arguments.size())
                argumentToCompare = arguments.get(index++);
            else
                return false;
            if(argument != Type.UNSPECIFIED && argumentToCompare != Type.UNSPECIFIED && argument != argumentToCompare)
                if(!(argument == Type.INT && argumentToCompare == Type.FLOAT))
                    if(!(argument == Type.FLOAT && argumentToCompare == Type.INT))
                        return false;
        }
        return true;
    }

    @Override
    public Function getElement(List<String> identifier) {
        if(identifier.size() == 0)
            return this;
        throw new RuntimeException("Bad function identifier"); //Programmer side error
    }

    @Override
    public void addElement(List<String> subList, Function element) {
        throw new RuntimeException("Cannot add function to a function"); //Programmer side error
    }
}