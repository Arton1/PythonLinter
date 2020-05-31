package linter.type_analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NameSpace {
    private Map<String, Variable> variables = new HashMap<String, Variable>();
    private Map<String, Function> functions = new HashMap<String, Function>();
    private Map<String, Class> classes = new HashMap<String, Class>();

    public void addVariable(Variable variable){
        variables.put(variable.getIdentifier(), variable);
    }

    public void addFunction(Function function){
        functions.put(function.getIdentifier(), function);
    }

    public void addClass(Class _class){
        classes.put(_class.getIdentifier(), _class);
    }

    private Class findClass(String identifier){
        Class _class = classes.get(identifier);
        if(_class == null)
            throw new RuntimeException("Class not created");
        _class.incrementNumberOfReferences(); //referenced class
        return _class;
    }

    public Variable getVariable(List<String> identifier){
        if(identifier.size() == 0)
            throw new RuntimeException("Cannot process identifier"); //programmer error
        else if(identifier.size() > 1)
            return findClass(identifier.remove(0)).getNameSpace().getVariable(identifier);
        else
            return variables.get(identifier.get(0));
    }

    public Function getFunction(List<String> identifier){
        if(identifier.size() == 0)
            throw new RuntimeException("Cannot process identifier"); //programmer error
        else if(identifier.size() > 1)
            return findClass(identifier.remove(0)).getNameSpace().getFunction(identifier);
        else
            return functions.get(identifier.get(0));
    }

    public Class getClass(List<String> identifier){
        if(identifier.size() == 0)
            throw new RuntimeException("Cannot process identifier"); //programmer error
        else if(identifier.size() > 1)
            return findClass(identifier.remove(0)).getNameSpace().getClass(identifier);
        else
            return classes.get(identifier.get(0));
    }

    public List<Variable> getUnusedVariables(){
        List<Variable> unusedVariables = new ArrayList<Variable>();
        for (Map.Entry<String, Variable> entry : variables.entrySet()){
            Variable variable = entry.getValue();
            if(variable.getNumberOfReferences() == 0)
                unusedVariables.add(variable);
        }
        for (Map.Entry<String, Class> entry : classes.entrySet()){
            Class _class = entry.getValue();
            unusedVariables.addAll(_class.getNameSpace().getUnusedVariables());
        }
        return unusedVariables;
    }

    public List<Function> getUnusedFunctions(){
        List<Function> unusedFunctions = new ArrayList<Function>();
        for (Map.Entry<String, Function> entry : functions.entrySet()){
            Function function = entry.getValue();
            if(function.getNumberOfReferences() == 0)
                unusedFunctions.add(function);
        }
        for (Map.Entry<String, Class> entry : classes.entrySet()){
            Class _class = entry.getValue();
            unusedFunctions.addAll(_class.getNameSpace().getUnusedFunctions());
        }
        return unusedFunctions;
    }

    public List<Class> getUnusedClasses(){
        List<Class> unusedClasses = new ArrayList<Class>();
        for (Map.Entry<String, Class> entry : classes.entrySet()){
            Class variable = entry.getValue();
            if(variable.getNumberOfReferences() == 0)
                unusedClasses.add(variable);
        }
        for (Map.Entry<String, Class> entry : classes.entrySet()){
            Class _class = entry.getValue();
            unusedClasses.addAll(_class.getNameSpace().getUnusedClasses());
        }
        return unusedClasses;
    }
}