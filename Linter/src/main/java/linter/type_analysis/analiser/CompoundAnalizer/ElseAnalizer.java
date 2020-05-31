package linter.type_analysis.analiser.CompoundAnalizer;

import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.compound_productions.OptionalElseProduction;
import linter.syntax_tree.production.compound_productions.SuiteProduction;
import linter.type_analysis.NameSpace;
import linter.type_analysis.Type;

public class ElseAnalizer extends CompoundAnalizer {

    protected ElseAnalizer(List<NameSpace> nameSpaceStack, List<NameSpace> retiredNameSpaces, NameSpace currentContextNameSpace, Type functionReturnType) {
        super(nameSpaceStack, retiredNameSpaces, currentContextNameSpace, functionReturnType);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(OptionalElseProduction.class))
            return true;
        int position = 0;
        Node child;
        addNewNameSpace();
        while((child = node.getChildAtPosition(position++)) != null){
            if (child.isType(SuiteProduction.class))
                processSuiteProduction(child);
        }
        removeCurrentNameSpace();
        return true;
    }

    private void processSuiteProduction(Node node) {
        SuiteAnalizer analizer = new SuiteAnalizer(nameSpaceStack, retiredNameSpaces, currentContextNameSpace, functionReturnType);
        node.accept(analizer);
    }
}