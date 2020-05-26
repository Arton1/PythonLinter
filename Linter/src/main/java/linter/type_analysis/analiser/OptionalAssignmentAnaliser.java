package linter.type_analysis.analiser;

import java.util.List;

import linter.syntax_tree.ProductionNode;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Type;

public class OptionalAssignmentAnaliser extends TypeAnaliser {

    protected OptionalAssignmentAnaliser(List<Table<Type>> variableTables, List<Table<Function>> functionTables) {
        super(variableTables, functionTables);
    }

    @Override
    public boolean visit(ProductionNode node) {
        // TODO Auto-generated method stub
        return false;
    }

}
