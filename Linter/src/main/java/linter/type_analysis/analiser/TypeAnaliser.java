package linter.type_analysis.analiser;

import java.util.ArrayList;
import java.util.List;

import linter.syntax_tree.NodeVisitor;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.TokenNode;
import linter.type_analysis.Function;
import linter.type_analysis.NameSpace;
import linter.type_analysis.Type;
import linter.type_analysis.Variable;
import linter.type_analysis.Class;

public abstract class TypeAnaliser implements NodeVisitor {

    protected List<NameSpace> nameSpaceStack;

    protected Function function = null;
    protected Variable variable = null;
    protected Type type = null;

    public abstract boolean visit(ProductionNode node);

    protected TypeAnaliser(List<NameSpace> nameSpaceStack){
        this.nameSpaceStack = nameSpaceStack;
    }

    public Type getType(){
        return type;
    }

    public Function getFunction(){
        return function;
    }

    public Variable getVariable(){
        return variable;
    }

    @Override
    public void visit(TokenNode node) {
        throw new RuntimeException("Token instead of production"); //Programmer error
    }

    protected Variable findVariable(List<String> identifier){
        Variable variable = null;
        for(int i=nameSpaceStack.size()-1; i>=0; i--){
            variable = nameSpaceStack.get(i).getVariable(identifier);
            if(variable != null)
                break;
        }
        return variable;
    }

    protected Function findFunction(List<String> identifier){
        Function function = null;
        for(int i=nameSpaceStack.size()-1; i>=0; i--){
            function = nameSpaceStack.get(i).getFunction(identifier);
            if(function != null)
                break;
        }
        return function;
    }

    protected Class findClass(String identifier){
        List<String> identifierList = new ArrayList<String>();
        identifierList.add(identifier);
        return findClass(identifierList);
    }

    protected Class findClass(List<String> identifier){
        Class _class = null;
        for(int i=nameSpaceStack.size()-1; i>=0; i--){
            _class = nameSpaceStack.get(i).getClass(identifier);
            if(function != null)
                break;
        }
        return _class;
    }
}