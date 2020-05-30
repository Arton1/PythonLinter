package linter.type_analysis.analiser.CompoundAnalizer;

import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.compound_productions.OptionalElseProduction;
import linter.syntax_tree.production.compound_productions.SuiteProduction;
import linter.syntax_tree.production.compound_productions.WhileStatementProduction;
import linter.syntax_tree.production.test_productions.TestProduction;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Type;
import linter.type_analysis.Variable;
import linter.type_analysis.analiser.TestAnaliser;

public class WhileStatementAnalizer extends CompoundAnalizer {

    public WhileStatementAnalizer(List<Table<Variable>> variableTables, List<Table<Function>> functionTables, List<Table<Variable>> retiredVariableTables, List<Table<Function>> retiredFunctionTables) {
        super(variableTables, functionTables, retiredVariableTables, retiredFunctionTables);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(WhileStatementProduction.class))
            return true;
        int position = 0;
        Node child;
        variableTables.add(new Table<Variable>());
        functionTables.add(new Table<Function>());
        while((child = node.getChildAtPosition(position++)) != null){
            if(child.isType(TestProduction.class))
                processTestProduction(child);
            else if (child.isType(SuiteProduction.class))
                processSuiteProduction(child);
            else if (child.isType(OptionalElseProduction.class))
                processElseProduction(child);
        }
        return true;
    }

    private void processTestProduction(Node node) {
        TestAnaliser analiser = new TestAnaliser(variableTables, functionTables);
        node.accept(analiser);
        Type returnedType = null;
        if(analiser.getVariable() != null){
            returnedType = analiser.getVariable().getType();
        }
        if(analiser.getType() != null){
            returnedType = analiser.getType();
        }
        //Dont check returned type, because Python doesnt care about type
    }

    private void processSuiteProduction(Node node) {
        SuiteAnalizer analizer = new SuiteAnalizer(variableTables, functionTables, retiredVariableTables, retiredFunctionTables);
        node.accept(analizer);
    }

    private void processElseProduction(Node node) {
        ElseAnalizer analizer = new ElseAnalizer(variableTables, functionTables, retiredVariableTables, retiredFunctionTables);
        node.accept(analizer);
    }
    
}