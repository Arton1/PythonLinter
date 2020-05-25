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