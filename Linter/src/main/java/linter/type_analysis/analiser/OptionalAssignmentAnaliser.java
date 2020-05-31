package linter.type_analysis.analiser;

import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.AnnualAssignmentStatementProduction;
import linter.syntax_tree.production.OptionalAssignmentStatementProduction;
import linter.syntax_tree.production.test_productions.ExpressionProduction;
import linter.type_analysis.NameSpace;
import linter.type_analysis.Variable;

public class OptionalAssignmentAnaliser extends ObjectCreatorAnalizer {

    boolean shouldCheckVariableType = false;

    protected OptionalAssignmentAnaliser(List<NameSpace> nameSpaceStack, NameSpace currentContextNameSpace) {
        super(nameSpaceStack, currentContextNameSpace);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(OptionalAssignmentStatementProduction.class))
            return true;
        int position = 0;
        Node child;
        while((child = node.getChildAtPosition(position++)) != null){
            if(child.isType(AnnualAssignmentStatementProduction.class))
                processAnnualAssignment(child);
            else if(child.isType(ExpressionProduction.class))
                processExpressionProduction(child);
        }
        return true;
    }

    private void processAnnualAssignment(Node node){
        AnnualAssignmentAnaliser analiser = new AnnualAssignmentAnaliser(nameSpaceStack, currentContextNameSpace);
        node.accept(analiser);
        if(analiser.getType() != null){
            type = analiser.getType();
            return;
        }
        if(analiser.getVariable() != null){
            variable = analiser.getVariable();
            return;
        }
    }

    private void processExpressionProduction(Node node){
        ExpressionAnaliser analiser = new ExpressionAnaliser(nameSpaceStack);
        node.accept(analiser);
        if(analiser.getType() != null){
            type = analiser.getType();
        }
        if(analiser.getVariable() != null){
            Variable variable = analiser.getVariable();
            if(variable.getType() == null)
                throw new RuntimeException("No variable type");
            type = variable.getType();
        }
        shouldCheckVariableType = true;
    }

    public boolean shouldCheckVariableType(){
        return shouldCheckVariableType;
    }

}
