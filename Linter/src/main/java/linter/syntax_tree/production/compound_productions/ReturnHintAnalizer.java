package linter.syntax_tree.production.compound_productions;

import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.TokenNode;
import linter.token.IdentifierToken;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Type;
import linter.type_analysis.Variable;
import linter.type_analysis.analiser.TypeAnaliser;

public class ReturnHintAnalizer extends TypeAnaliser {

    Type returnType = null;

    public ReturnHintAnalizer(List<Table<Variable>> variableTables, List<Table<Function>> functionTables) {
        super(variableTables, functionTables);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(OptionalReturnHintProduction.class))
            return true;
        int position = 0;
        Node child;
        while((child = node.getChildAtPosition(position++)) != null){
            if (child instanceof TokenNode)
                child.accept(this);
        }
        return true;
    }

    @Override
    public void visit(TokenNode node){
        if(node.getToken() instanceof IdentifierToken){
            String returnString = ((IdentifierToken)node.getToken()).getIdentifier();
            returnType = Type.fromString(returnString);
        }
    }

    public Type getReturnType(){
        return returnType;
    }
    
}