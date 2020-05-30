package linter.type_analysis.analiser;

import java.util.List;

import linter.syntax_tree.NodeVisitor;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.TokenNode;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Type;
import linter.type_analysis.Variable;

public abstract class TypeAnaliser implements NodeVisitor {

    protected List<Table<Variable>> variableTables;
    protected List<Table<Function>> functionTables;

    protected Function function = null;
    protected Variable variable = null;
    protected Type type = null;

    public abstract boolean visit(ProductionNode node);

    protected TypeAnaliser(List<Table<Variable>> variableTables, List<Table<Function>> functionTables){
        this.variableTables = variableTables;
        this.functionTables = functionTables;
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

    protected Function findFunction(List<String> identifier){
        Function function = null;
        for(Table<Function> functionTable : functionTables){
            function = functionTable.getElement(identifier);
            if(function != null)
                break;
        }
        if(function == null)
            throw new RuntimeException("No function found");
        return function;
    }

    protected Variable findVariable(List<String> identifier){
        Variable variable = null;
        for(int i=variableTables.size()-1; i>=0; i--){
            variable = variableTables.get(i).getElement(identifier);
            if(variable != null)
                break;
        }
        return variable;
    }
    
    protected void saveVariable(Variable variable){
        Table<Variable> variableTable = variableTables.get(variableTables.size()-1);
        variableTable.addElement(variable.getIdentifier(), variable);
    }
}