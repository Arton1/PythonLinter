package linter.type_analysis.analiser.CompoundAnalizer;

import java.util.LinkedList;
import java.util.List;

import linter.syntax_tree.ProductionNode;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Variable;
import linter.type_analysis.analiser.TypeAnaliser;

public abstract class CompoundAnalizer extends TypeAnaliser {

    protected List<Table<Variable>> retiredVariableTables = new LinkedList<Table<Variable>>();
    protected List<Table<Function>> retiredFunctionTables = new LinkedList<Table<Function>>();

    protected CompoundAnalizer(List<Table<Variable>> variableTables, List<Table<Function>> functionTables, List<Table<Variable>> retiredVariableTables, List<Table<Function>> retiredFunctionTables){
        super(variableTables, functionTables);
        this.retiredVariableTables = retiredVariableTables;
        this.retiredFunctionTables = retiredFunctionTables;
    }

    public abstract boolean visit(ProductionNode node);
}