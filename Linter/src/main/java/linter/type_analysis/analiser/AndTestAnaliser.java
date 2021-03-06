package linter.type_analysis.analiser;

import java.util.List;

import linter.exception.SemanticsException;
import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.test_productions.AndTestProduction;
import linter.syntax_tree.production.test_productions.NotTestProduction;
import linter.type_analysis.NameSpace;
import linter.type_analysis.Type;

public class AndTestAnaliser extends TypeAnaliser {

    protected AndTestAnaliser(List<NameSpace> nameSpaceStack) {
        super(nameSpaceStack);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(AndTestProduction.class))
            return true;
        int position = 0;
        Node child;
        while((child = node.getChildAtPosition(position++)) != null){
            if(child.isType(NotTestProduction.class))
                processNotTestProduction(child);
        }
        return true;
    }

    private void processNotTestProduction(Node node){
        NotTestAnaliser analiser = new NotTestAnaliser(nameSpaceStack);
        node.accept(analiser);
        if(variable != null || type != null){
            if(variable != null && variable.getType() == null)
                throw new SemanticsException("Uninitialized variable", node.getSubtreeFirstToken());
            variable = null;
            type = Type.BOOL;
            return;
        }
        if(analiser.getVariable() != null){
            variable = analiser.getVariable();
            return;
        }
        if(analiser.getType() != null){
            type = analiser.getType();
            return;
        }
        throw new RuntimeException("Nothing received from analyzer"); //Programmer side error
    }
}