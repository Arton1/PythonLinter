package linter.type_analysis.analiser;

import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Type;
import linter.syntax_tree.production.OptionalAssignmentStatementProduction;
import linter.syntax_tree.production.SimpleStatementProduction;
import linter.syntax_tree.production.test_productions.TestProduction;

public class SimpleStatementAnalizer extends TypeAnaliser {

    public SimpleStatementAnalizer(List<Table<Type>> variableTables, List<Table<Function>> functionTables) {
        super(variableTables, functionTables);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(SimpleStatementProduction.class)) //check production type
            return true;
        Node child = node.getChildAtPosition(0);
        if(!child.isType(TestProduction.class))
            return true;
        Node secondChild = node.getChildAtPosition(1);
        if(!secondChild.isType(OptionalAssignmentStatementProduction.class))
            return true; //TODO: Process function call
        //There is an assignment in a subtree!
        TestAnaliser testAnaliser = new TestAnaliser(variableTables, functionTables);
        child.accept(testAnaliser);
        OptionalAssignmentAnaliser assignmentAnaliser = new OptionalAssignmentAnaliser(variableTables, functionTables);
        secondChild.accept(assignmentAnaliser);

        return true; //Dont go to children
    }

}