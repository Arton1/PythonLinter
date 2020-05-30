package linter.type_analysis.analiser;

import java.util.ArrayList;
import java.util.List;

import linter.exception.SemanticsException;
import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.AnnualAssignmentStatementProduction;
import linter.syntax_tree.production.test_productions.ExpressionProduction;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Type;
import linter.type_analysis.Variable;

public class AnnualAssignmentAnaliser extends TypeAnaliser {

    List<Variable> variablesToAssignTo = new ArrayList<Variable>();
    Type typeToAssign = null;

    protected AnnualAssignmentAnaliser(List<Table<Variable>> variableTables, List<Table<Function>> functionTables) {
        super(variableTables, functionTables);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(AnnualAssignmentStatementProduction.class))
            return true;
        int position = 0;
        Node child;
        while((child = node.getChildAtPosition(position++)) != null){
            if(child.isType(ExpressionProduction.class))
                processExpressionProduction(child);
        }
        if(typeToAssign == null){
            Variable assignedVariable = variablesToAssignTo.get(variablesToAssignTo.size()-1);
            if(assignedVariable.getType() == null)
                throw new SemanticsException("Non initialized variable", node.getParent().getParent().getSubtreeFirstToken());
            typeToAssign = assignedVariable.getType();
        }
        for(Variable variable : variablesToAssignTo){
            variable.setType(typeToAssign);
            saveVariable(variable);
        }
        type = typeToAssign;
        return true;
    }

    private void processExpressionProduction(Node node){
        if(typeToAssign != null)
            throw new SemanticsException("Cannot assign to non-variable", node.getParent().getParent().getSubtreeFirstToken());
        ExpressionAnaliser analiser = new ExpressionAnaliser(variableTables, functionTables);
        node.accept(analiser);
        if(analiser.getType() != null){
            typeToAssign = analiser.getType();
            return;
        }
        if(analiser.getVariable() != null){
            variablesToAssignTo.add(analiser.getVariable());
            return;
        }
    }
    
}