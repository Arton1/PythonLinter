package linter.type_analysis.analiser.CompoundAnalizer;

import java.util.ArrayList;
import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.compound_productions.FunctionArgumentsProdution;
import linter.syntax_tree.production.compound_productions.FunctionParametersProduction;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Variable;
import linter.type_analysis.analiser.TypeAnaliser;

public class FunctionParametersAnalizer extends TypeAnaliser {

    private List<Variable> arguments = new ArrayList<Variable>();

    protected FunctionParametersAnalizer(List<Table<Variable>> variableTables, List<Table<Function>> functionTables) {
        super(variableTables, functionTables);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(FunctionParametersProduction.class))
            return true;
        int position = 0;
        Node child;
        while((child = node.getChildAtPosition(position++)) != null){
            if(child.isType(FunctionArgumentsProdution.class))
                processFunctionArguments(child);
        }
        return true;
    }

    private void processFunctionArguments(Node child) {
        FunctionArgumentsAnalizer analiser = new FunctionArgumentsAnalizer(variableTables, functionTables);
        child.accept(analiser);
        arguments = analiser.getArguments();
    }

    public List<Variable> getArguments(){
        return arguments;
    }
    
}