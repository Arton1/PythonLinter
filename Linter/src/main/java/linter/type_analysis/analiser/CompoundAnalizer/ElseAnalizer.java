package linter.type_analysis.analiser.CompoundAnalizer;

import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.compound_productions.OptionalElseProduction;
import linter.syntax_tree.production.compound_productions.SuiteProduction;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Variable;

public class ElseAnalizer extends CompoundAnalizer {

    protected ElseAnalizer(List<Table<Variable>> variableTables, List<Table<Function>> functionTables,
            List<Table<Variable>> retiredVariableTables, List<Table<Function>> retiredFunctionTables) {
        super(variableTables, functionTables, retiredVariableTables, retiredFunctionTables);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(OptionalElseProduction.class))
            return true;
        int position = 0;
        Node child;
        variableTables.add(new Table<Variable>());
        functionTables.add(new Table<Function>());
        while((child = node.getChildAtPosition(position++)) != null){
            if (child.isType(SuiteProduction.class))
                processSuiteProduction(child);
        }
        return true;
    }

    private void processSuiteProduction(Node node) {
        SuiteAnalizer analizer = new SuiteAnalizer(variableTables, functionTables, retiredVariableTables, retiredFunctionTables);
        node.accept(analizer);
    }
}