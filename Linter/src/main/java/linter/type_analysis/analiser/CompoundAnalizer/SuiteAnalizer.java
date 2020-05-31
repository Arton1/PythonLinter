package linter.type_analysis.analiser.CompoundAnalizer;

import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.SimpleStatementProduction;
import linter.syntax_tree.production.compound_productions.CompoundStatementProduction;
import linter.syntax_tree.production.compound_productions.SuiteProduction;
import linter.type_analysis.NameSpace;
import linter.type_analysis.Type;
import linter.type_analysis.analiser.SimpleStatementAnalizer;

public class SuiteAnalizer extends CompoundAnalizer {

    public SuiteAnalizer(List<NameSpace> nameSpaceStack, List<NameSpace> retiredNameSpaces, NameSpace currentContextNameSpace, Type functionReturnType) {
        super(nameSpaceStack, retiredNameSpaces, currentContextNameSpace,functionReturnType);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(SuiteProduction.class))
            return true;
        int position = 0;
        Node child;
        while((child = node.getChildAtPosition(position++)) != null){
            if(child.isType(SimpleStatementProduction.class))
                processSimpleProduction(child);
            else if (child.isType(CompoundStatementProduction.class))
                processCompoundProduction(child);
        }
        return true;
    }

    private void processSimpleProduction(Node node) {
        SimpleStatementAnalizer analizer = new SimpleStatementAnalizer(nameSpaceStack, currentContextNameSpace, functionReturnType);
        node.accept(analizer);    
    }
    
    private void processCompoundProduction(Node node) {
        CompoundStatementAnalizer analizer = new CompoundStatementAnalizer(nameSpaceStack, retiredNameSpaces, currentContextNameSpace, functionReturnType);
        node.accept(analizer);
    }
}