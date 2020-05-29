package linter.type_analysis;

import java.util.ArrayList;
import java.util.List;

public class Function implements TableElement<Function> {
    private Type returnType;
    private List<Type> arguments = new ArrayList<Type>();

    public Function(Type returnType, List<Type> arguments){
        this.returnType = returnType;
        this.arguments = arguments;
    }

    public Type getReturnType(){
        return returnType;
    }

    public List<Type> getArguments(){
        return arguments;
    }

    public boolean compareArgumentTypes(List<Type> arguments){
        int index = 0;
        Type argumentToCompare;
        for(Type argument : this.arguments){
            if(index < arguments.size())
                argumentToCompare = arguments.get(index++);
            else
                return false;
            if(!argument.equals(argumentToCompare))
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