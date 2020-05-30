package linter.type_analysis.analiser.CompoundAnalizer;

import java.util.List;

import linter.exception.SemanticsException;
import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.SimpleStatementProduction;
import linter.syntax_tree.production.compound_productions.CompoundStatementProduction;
import linter.syntax_tree.production.compound_productions.SuiteProduction;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Variable;
import linter.type_analysis.analiser.SimpleStatementAnalizer;

public class SuiteAnalizer extends CompoundAnalizer {

    public SuiteAnalizer(List<Table<Variable>> variableTables, List<Table<Function>> functionTables, List<Table<Variable>> retiredVariableTables, List<Table<Function>> retiredFunctionTables) {
        super(variableTables, functionTables, retiredVariableTables, retiredFunctionTables);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(SuiteProduction.class))
            return true;
        int position = 0;
        Node child;
        variableTables.add(new Table<Variable>());
        functionTables.add(new Table<Function>());
        while((child = node.getChildAtPosition(position++)) != null){
            if(child.isType(SimpleStatementProduction.class))
                processSimpleProduction(child);
            else if (child.isType(CompoundStatementProduction.class))
                processCompoundProduction(child);
        }
        retiredVariableTables.add(variableTables.remove(variableTables.size()-1));
        retiredFunctionTables.add(functionTables.remove(functionTables.size()-1));
        return true;
    }

    private void processSimpleProduction(Node node) {
        SimpleStatementAnalizer analizer = new SimpleStatementAnalizer(variableTables, functionTables);
        node.accept(analizer);    
    }
    
    private void processCompoundProduction(Node node) {
        CompoundStatementAnalizer analizer = new CompoundStatementAnalizer(variableTables, functionTables, retiredVariableTables, retiredFunctionTables);
        node.accept(analizer);
    }
}