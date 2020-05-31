package linter.type_analysis.analiser.CompoundAnalizer;

import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.TokenNode;
import linter.syntax_tree.production.compound_productions.FunctionArgumentProduction;
import linter.token.IdentifierToken;
import linter.type_analysis.NameSpace;
import linter.type_analysis.Type;
import linter.type_analysis.Variable;
import linter.type_analysis.analiser.TypeAnaliser;

public class FunctionArgumentAnalizer extends TypeAnaliser {
    private String identifier = null;
    private Type type = null;
    private Variable variable;
    private int line, column;

    protected FunctionArgumentAnalizer(List<NameSpace> nameSpaceStack) {
        super(nameSpaceStack);
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
        Variable variable = new Variable(identifier, null, line, column); //function argument doesnt belong to a class
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
            IdentifierToken token = (IdentifierToken)node.getToken();
            String string = token.getIdentifier();
            line = token.getLine();
            column = token.getColumn();
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