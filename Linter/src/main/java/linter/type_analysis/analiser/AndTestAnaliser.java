package linter.type_analysis.analiser;

import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.test_productions.AndTestProduction;
import linter.syntax_tree.production.test_productions.NotTestProduction;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Type;

public class AndTestAnaliser extends TypeAnaliser {

    protected AndTestAnaliser(List<Table<Type>> variableTables, List<Table<Function>> functionTables) {
        super(variableTables, functionTables);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(AndTestProduction.class))
            return true;
        int position = 0;
        Node child;
        while((child = node.getChildAtPosition(position++)) != null){
            if(!child.isType(NotTestProduction.class))
                continue;
            NotTestAnaliser analiser = new NotTestAnaliser(variableTables, functionTables);
            child.accept(analiser);
        }
        return true;
    }

}
