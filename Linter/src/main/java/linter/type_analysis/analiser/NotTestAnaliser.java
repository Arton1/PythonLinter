package linter.type_analysis.analiser;

import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.test_productions.ComparisonProduction;
import linter.syntax_tree.production.test_productions.NotTestProduction;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Type;

public class NotTestAnaliser extends TypeAnaliser {

    protected NotTestAnaliser(List<Table<Type>> variableTables, List<Table<Function>> functionTables) {
        super(variableTables, functionTables);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(NotTestProduction.class))
            return true;
        int position = 0;
        Node child;
        while((child = node.getChildAtPosition(position++)) != null){
            if(child.isType(ComparisonProduction.class)){
                ComparisonAnaliser analiser = new ComparisonAnaliser(variableTables, functionTables);
            }
            else
                if(child.isType(NotTestProduction.class)){
                    child.accept(this);
                }
        }
        return true;
    }
}