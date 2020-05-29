package linter.type_analysis.analiser;

import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Variable;
import linter.syntax_tree.production.OptionalAssignmentStatementProduction;
import linter.syntax_tree.production.SimpleStatementProduction;
import linter.syntax_tree.production.test_productions.TestProduction;

public class SimpleStatementAnalizer extends TypeAnaliser {

    public SimpleStatementAnalizer(List<Table<Variable>> variableTables, List<Table<Function>> functionTables) {
        super(variableTables, functionTables);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(SimpleStatementProduction.class))
            return true;
        int position = 0;
        Node child;
        while((child = node.getChildAtPosition(position++)) != null){
            if(child.isType(TestProduction.class))
                procesTestProduction(child);
            else if(child.isType(OptionalAssignmentStatementProduction.class))
                processAssignmentProduction(child);
        }
        return true;
    }

    private void procesTestProduction(Node node){
        TestAnaliser analiser = new TestAnaliser(variableTables, functionTables);
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

    private void processAssignmentProduction(Node node){
        OptionalAssignmentAnaliser analiser = new OptionalAssignmentAnaliser(variableTables, functionTables);
        node.accept(analiser);
        if(variable == null)
            throw new RuntimeException("No variable to assign to");
        variable.setType(analiser.getType());
        for(String identifierPart : variable.getIdentifier())
            System.out.println(identifierPart);
        System.out.print(analiser.getType());
        saveVariable(variable);
    }

    private void saveVariable(Variable variable){
        Table<Variable> variableTable = variableTables.get(variableTables.size()-1);
        variableTable.addElement(variable.getIdentifier(), variable);
    }

}