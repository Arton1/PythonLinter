package linter.type_analysis.analiser;

import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.test_productions.ComparisonProduction;
import linter.syntax_tree.production.test_productions.ExpressionProduction;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Type;

public class ComparisonAnaliser extends TypeAnaliser {

    protected ComparisonAnaliser(List<Table<Type>> variableTables, List<Table<Function>> functionTables) {
        super(variableTables, functionTables);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(ComparisonProduction.class))
            return true;
        int position = 0;
        Node child;
        while((child = node.getChildAtPosition(position++)) != null){
            if(!child.isType(ExpressionProduction.class))
                continue;
            ExpressionAnaliser analiser = new ExpressionAnaliser(variableTables, functionTables);
            child.accept(analiser);
        }
        return true;
    }

}
