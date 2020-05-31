package linter.type_analysis.analiser.CompoundAnalizer;

import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.compound_productions.OptionalElifsProduction;
import linter.syntax_tree.production.compound_productions.SuiteProduction;
import linter.syntax_tree.production.test_productions.TestProduction;
import linter.type_analysis.NameSpace;
import linter.type_analysis.Type;
import linter.type_analysis.analiser.TestAnaliser;

public class ElifsAnalizer extends CompoundAnalizer {

    protected ElifsAnalizer(List<NameSpace> nameSpaceStack, List<NameSpace> retiredNameSpaces, NameSpace currentContextNameSpace, Type functionReturnType) {
        super(nameSpaceStack, retiredNameSpaces, currentContextNameSpace, functionReturnType);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(OptionalElifsProduction.class))
            return true;
        int position = 0;
        Node child;
        addNewNameSpace();
        while((child = node.getChildAtPosition(position++)) != null){
            if(child.isType(TestProduction.class))
                processTestProduction(child);
            else if (child.isType(SuiteProduction.class))
                processSuiteProduction(child);
        }
        removeCurrentNameSpace();
        return true;
    }

    private void processTestProduction(Node node) {
        TestAnaliser analiser = new TestAnaliser(nameSpaceStack);
        node.accept(analiser);
        Type returnedType = null;
        if(analiser.getVariable() != null){
            returnedType = analiser.getVariable().getType();
        }
        if(analiser.getType() != null){
            returnedType = analiser.getType();
        }
        //Dont check returned type, because Python doesnt care about type here
    }

    private void processSuiteProduction(Node node) {
        SuiteAnalizer analizer = new SuiteAnalizer(nameSpaceStack, retiredNameSpaces, currentContextNameSpace, functionReturnType);
        node.accept(analizer);
    }
    
}