package linter.type_analysis.analiser;

import java.util.List;

import linter.exception.SemanticsException;
import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.test_productions.ComparisonProduction;
import linter.syntax_tree.production.test_productions.NotTestProduction;
import linter.type_analysis.NameSpace;
import linter.type_analysis.Type;

public class NotTestAnaliser extends TypeAnaliser {

    protected NotTestAnaliser(List<NameSpace> nameSpaceStack) {
        super(nameSpaceStack);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(NotTestProduction.class))
            return true;
        int position = 0;
        Node child;
        while((child = node.getChildAtPosition(position++)) != null){
            if(child.isType(ComparisonProduction.class))
                processComparisonProduction(child);
            else
                if(child.isType(NotTestProduction.class))
                    processNotTestProduction(child);
        }
        return true;
    }

    private void processComparisonProduction(Node node){
        ComparisonAnaliser analiser = new ComparisonAnaliser(nameSpaceStack);
        node.accept(analiser);
        if(analiser.getType() != null){
            type = analiser.getType();
            return;
        }
        if(analiser.getVariable() != null){
            variable = analiser.getVariable();
            return;
        }
        throw new RuntimeException("Nothing received from analyzer");
    }

    private void processNotTestProduction(Node node){
        node.accept(this);
        if(variable != null && variable.getType() == null)
            throw new SemanticsException("Uninitialized variable", node.getSubtreeFirstToken());
        variable = null;
        type = Type.BOOL;
    }
}