package linter.type_analysis.analiser.CompoundAnalizer;

import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.TokenNode;
import linter.syntax_tree.production.compound_productions.FunctionArgumentProduction;
import linter.token.IdentifierToken;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Type;
import linter.type_analysis.Variable;
import linter.type_analysis.analiser.TypeAnaliser;

public class FunctionArgumentAnalizer extends TypeAnaliser {
    private String identifier = null;
    private Type type = null;
    private Variable variable;

    protected FunctionArgumentAnalizer(List<Table<Variable>> variableTables, List<Table<Function>> functionTables) {
        super(variableTables, functionTables);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if (!node.isType(FunctionArgumentProduction.class))
            return true;
        int position = 0;
        Node child;
        while ((child = node.getChildAtPosition(position++)) != null) {
            if (child instanceof TokenNode)
                child.accept(this);
        }
        Variable variable = new Variable(identifier);
        if(type == null)
            variable.setType(Type.UNSPECIFIED);
        else
            variable.setType(type);
        this.variable = variable;
        return true;
    }

    @Override
    public void visit(TokenNode node){
        if(node.getToken() instanceof IdentifierToken){
            String string = ((IdentifierToken)node.getToken()).getIdentifier();
            if(identifier == null)
                identifier = string;
            else
                type = Type.fromString(string);
        }
    }

    public Variable getVariable(){
        return variable;
    }
    
}