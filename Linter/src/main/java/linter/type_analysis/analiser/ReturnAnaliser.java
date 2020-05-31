package linter.type_analysis.analiser;

import java.util.List;

import linter.exception.SemanticsException;
import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.ReturnStatementProduction;
import linter.syntax_tree.production.test_productions.TestProduction;
import linter.token.Token;
import linter.type_analysis.NameSpace;
import linter.type_analysis.Type;

public class ReturnAnaliser extends TypeAnaliser {

    Type returnedType = Type.UNSPECIFIED;
    Type functionReturnType;
    Token returnTypeToken = null;

    protected ReturnAnaliser(List<NameSpace> nameSpaceStack, Type functionReturnType) {
        super(nameSpaceStack);
        this.functionReturnType = functionReturnType;
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(ReturnStatementProduction.class))
            return true;
        int position = 0;
        Node child;
        while((child = node.getChildAtPosition(position++)) != null)
            if (child.isType(TestProduction.class))
                processTestProduction(child);
        checkReturnedType(node);
        return true;
    }

    private void processTestProduction(Node node) {
        TestAnaliser analizer = new TestAnaliser(nameSpaceStack);
        node.accept(analizer);
        if(analizer.getVariable() != null)
            returnedType = analizer.getVariable().getType();
        else if(analizer.getType() != null)
            returnedType = analizer.getType();
        else
            throw new RuntimeException("Nothing received from analyzer");
        returnTypeToken = node.getSubtreeFirstToken();
    }
    
    private void checkReturnedType(Node node){
        if(functionReturnType == null)
            throw new SemanticsException("Return thrown at global scope", node.getSubtreeFirstToken());
        if(returnTypeToken == null)
            returnTypeToken = node.getSubtreeFirstToken();
        if(functionReturnType == Type.UNSPECIFIED && returnedType != Type.UNSPECIFIED)
            throw new SemanticsException("Function shouldn't return anything", returnTypeToken);
        if(returnedType != functionReturnType){
            if(functionReturnType == Type.FLOAT || functionReturnType == Type.INT){
                if(!(type == Type.FLOAT || type == Type.INT))
                    throw new SemanticsException("Bad return type", returnTypeToken);
            }
            else
                throw new SemanticsException("Bad return type", returnTypeToken);
        }
    }
}