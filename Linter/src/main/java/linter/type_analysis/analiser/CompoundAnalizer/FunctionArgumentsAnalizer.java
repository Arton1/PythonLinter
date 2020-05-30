package linter.type_analysis.analiser.CompoundAnalizer;

import java.util.ArrayList;
import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.compound_productions.FunctionArgumentProduction;
import linter.syntax_tree.production.compound_productions.FunctionArgumentsProdution;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Variable;
import linter.type_analysis.analiser.TypeAnaliser;

public class FunctionArgumentsAnalizer extends TypeAnaliser {

    private List<Variable> arguments = new ArrayList<Variable>();

    protected FunctionArgumentsAnalizer(List<Table<Variable>> variableTables, List<Table<Function>> functionTables) {
        super(variableTables, functionTables);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if (!node.isType(FunctionArgumentsProdution.class))
            return true;
        int position = 0;
        Node child;
        while ((child = node.getChildAtPosition(position++)) != null) {
            if (child.isType(FunctionArgumentProduction.class))
                processFunctionArgument(child);
        }
        return true;
    }

    private void processFunctionArgument(Node child) {
        FunctionArgumentAnalizer analizer = new FunctionArgumentAnalizer(variableTables, functionTables);
        child.accept(analizer);
        arguments.add(analizer.getVariable());
    }

    public List<Variable> getArguments(){
        return arguments;
    }
    
}