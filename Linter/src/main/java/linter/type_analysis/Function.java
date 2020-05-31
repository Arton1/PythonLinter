package linter.type_analysis;

import java.util.ArrayList;
import java.util.List;

public class Function extends LanguageObject {
    
    private Type returnType;
    private List<Type> arguments = new ArrayList<Type>();

    public Function(String identifier, Type returnType, List<Variable> arguments, int line, int column){
        super(identifier, line, column);
        this.returnType = returnType;
        for(Variable argument : arguments)
            this.arguments.add(argument.getType());
    }

    public Type getReturnType(){
        return returnType;
    }

    public List<Type> getArguments(){
        return arguments;
    }

    public boolean compareArgumentTypes(List<Type> arguments){
        if(arguments.size() != this.arguments.size())
            return false;
        int index = 0;
        Type argumentToCompare;
        for(Type argument : this.arguments){
            argumentToCompare = arguments.get(index++);
            if(argument != Type.UNSPECIFIED && argumentToCompare != Type.UNSPECIFIED && argument != argumentToCompare)
                if(!(argument == Type.INT && argumentToCompare == Type.FLOAT))
                    if(!(argument == Type.FLOAT && argumentToCompare == Type.INT))
                        return false;
        }
        return true;
    }
}