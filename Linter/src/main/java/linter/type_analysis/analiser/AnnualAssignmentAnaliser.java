package linter.type_analysis.analiser;

import java.util.ArrayList;
import java.util.List;

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
        for(Variable variable : variablesToAssignTo){
            variable.setType(typeToAssign);
            saveVariable(variable);
        }
        type = typeToAssign;
        return true;
    }

    private void processExpressionProduction(Node node){
        if(typeToAssign != null)
            throw new RuntimeException("Cannot assign to non-variable");
        ExpressionAnaliser analiser = new ExpressionAnaliser(variableTables, functionTables);
        node.accept(analiser);
        if(analiser.getType() != null){
            typeToAssign = analiser.getType();
            return;
        }
        if(analiser.getVariable() != null){
            variablesToAssignTo.add(variable);
            return;
        }
    }

    private void saveVariable(Variable variable){
        Table<Variable> variableTable = variableTables.get(variableTables.size()-1);
        variableTable.addElement(variable.getIdentifier(), variable);
    }
    
}