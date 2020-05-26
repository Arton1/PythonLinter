package linter.type_analysis.analiser;

import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.test_productions.AtomicExpressionProduction;
import linter.syntax_tree.production.test_productions.FactorProduction;
import linter.syntax_tree.production.test_productions.PowerProduction;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Type;

public class PowerAnaliser extends TypeAnaliser {

    protected PowerAnaliser(List<Table<Type>> variableTables, List<Table<Function>> functionTables) {
        super(variableTables, functionTables);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(PowerProduction.class))
            return true;
        int position = 0;
        Node child;
        while((child = node.getChildAtPosition(position++)) != null){
            if(child.isType(AtomicExpressionProduction.class)){
                AtomicExpressionAnaliser analiser = new AtomicExpressionAnaliser(variableTables, functionTables);
            }
            else
                if(child.isType(FactorProduction.class)){
                    FactorAnaliser analiser = new FactorAnaliser(variableTables, functionTables);
                    child.accept(analiser);
                }
        }
        return true;
    }

}
