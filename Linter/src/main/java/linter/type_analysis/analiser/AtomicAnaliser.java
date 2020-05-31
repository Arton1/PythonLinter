package linter.type_analysis.analiser;

import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.TokenNode;
import linter.syntax_tree.production.test_productions.AtomicProduction;
import linter.syntax_tree.production.test_productions.TestListProduction;
import linter.token.DoubleNumberToken;
import linter.token.IdentifierToken;
import linter.token.IntegerNumberToken;
import linter.token.type.BracketTokenType;
import linter.token.type.LogicTokenType;
import linter.token.type.NullTokenType;
import linter.type_analysis.NameSpace;
import linter.type_analysis.Type;

public class AtomicAnaliser extends TypeAnaliser {
    private String identifier;

    protected AtomicAnaliser(List<NameSpace> nameSpaceStack) {
        super(nameSpaceStack);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(AtomicProduction.class))
            return true;
        int position = 0;
        Node child;
        while((child = node.getChildAtPosition(position++)) != null)
            if (child instanceof TokenNode)
                child.accept(this);
            else if (child.isType(TestListProduction.class))
                processTestListProduction(child);
        return true;
    }

    private void processTestListProduction(Node node) {
        TestListAnalizer analizer = new TestListAnalizer(nameSpaceStack);
        node.accept(analizer);
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public void visit(TokenNode node) {
        if(node.getToken() instanceof IdentifierToken)
            identifier = ((IdentifierToken)node.getToken()).getIdentifier();
        else if(node.getToken().getTokenType() instanceof LogicTokenType)
            type = Type.BOOL;
        else if(node.getToken().getTokenType() instanceof NullTokenType)
            type = Type.NONE;
        else if(node.getToken() instanceof IntegerNumberToken)
            type = Type.INT;
        else if(node.getToken() instanceof DoubleNumberToken)
            type = Type.FLOAT;
        else if(node.getToken().getTokenType() == BracketTokenType.ROUNDED_BEGIN)
            type = Type.TUPLE;
        else if(node.getToken().getTokenType() == BracketTokenType.SQUARED_BEGIN)
            type = Type.LIST;
    }
}

