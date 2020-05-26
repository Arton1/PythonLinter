package linter.type_analysis.analiser;

import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.TokenNode;
import linter.syntax_tree.production.test_productions.AtomicProduction;
import linter.token.DoubleNumberToken;
import linter.token.IdentifierToken;
import linter.token.IntegerNumberToken;
import linter.token.type.LogicTokenType;
import linter.token.type.NullTokenType;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Type;

public class AtomicAnaliser extends TypeAnaliser {

    protected AtomicAnaliser(List<Table<Type>> variableTables, List<Table<Function>> functionTables) {
        super(variableTables, functionTables);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(AtomicProduction.class))
            return true;
        int position = 0;
        Node child;
        while((child = node.getChildAtPosition(position++)) != null){
            child.accept(this); //change this
        }
        return true;
    }

    @Override
    public void visit(TokenNode node) {
        if(node.getToken() instanceof IdentifierToken)
            addIdentifier(((IdentifierToken)node.getToken()).getIdentifier());
        else if(node.getToken().getTokenType() instanceof LogicTokenType)
            type = Type.BOOL;
        else if(node.getToken().getTokenType() instanceof NullTokenType)
            type = Type.NONE;
        else if(node.getToken() instanceof IntegerNumberToken)
            type = Type.INT;
        else if(node.getToken() instanceof DoubleNumberToken)
            type = Type.FLOAT;
    }
}

