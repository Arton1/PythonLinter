package linter.type_analysis.analiser;

import java.util.ArrayList;
import java.util.List;

import linter.syntax_tree.NodeVisitor;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.TokenNode;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Type;

public abstract class TypeAnaliser implements NodeVisitor {

    List<Table<Type>> variableTables;
    List<Table<Function>> functionTables;

    boolean hasFunction = false;
    List<String> identifier = null;
    Type type = null;

    public abstract boolean visit(ProductionNode node);

    protected TypeAnaliser(List<Table<Type>> variableTables, List<Table<Function>> functionTables){
        this.variableTables = variableTables;
        this.functionTables = functionTables;
    }

    public Type getType(){
        return type;
    }

    public List<String> getIdentifier() {
        return identifier;
    }

    protected void addIdentifier(String identifier){
        if(this.identifier == null)
            this.identifier = new ArrayList<String>();
        this.identifier.add(identifier);
    }

    protected void addIdentifier(List<String> identifier) {
        if(this.identifier == null)
            this.identifier = identifier;
        else
            this.identifier.addAll(identifier);
    }

    public boolean hasFunction(){
        return hasFunction;
    }

    @Override
    public void visit(TokenNode node) {
        throw new RuntimeException("Token instead of production"); //Programmer error
    }
    
}