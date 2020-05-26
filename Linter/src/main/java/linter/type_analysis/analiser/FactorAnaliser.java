package linter.type_analysis.analiser;

import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.test_productions.FactorProduction;
import linter.syntax_tree.production.test_productions.PowerProduction;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Type;

public class FactorAnaliser extends TypeAnaliser {

    protected FactorAnaliser(List<Table<Type>> variableTables, List<Table<Function>> functionTables) {
        super(variableTables, functionTables);
    }

    @Override
	public boolean visit(ProductionNode node){
        if(!node.isType(FactorProduction.class))
            return true;
        int position = 0;
        Node child;
        while((child = node.getChildAtPosition(position++)) != null){
            if(child.isType(PowerProduction.class)){
                PowerAnaliser analiser = new PowerAnaliser(variableTables, functionTables);
            }
            else
                if(child.isType(FactorProduction.class)){
                    child.accept(this);
                }
        }
        return true;
    }

}
