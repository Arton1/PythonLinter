package linter.type_analysis.analiser;

import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.test_productions.TestListProduction;
import linter.syntax_tree.production.test_productions.TestProduction;
import linter.type_analysis.NameSpace;

public class TestListAnalizer extends TypeAnaliser {

    protected TestListAnalizer(List<NameSpace> nameSpaceStack) {
        super(nameSpaceStack);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(TestListProduction.class))
            return true;
        int position = 0;
        Node child;
        while((child = node.getChildAtPosition(position++)) != null)
            if (child.isType(TestProduction.class))
                processTestProduction(child);
        return true;
    }

    private void processTestProduction(Node child) {
        TestAnaliser analiser = new TestAnaliser(nameSpaceStack);
        child.accept(analiser);
        if(analiser.getType() != null)
            return;
        if(analiser.getVariable() != null)
            return;
        throw new RuntimeException("Nothing received from analyzer");
    }
    
}