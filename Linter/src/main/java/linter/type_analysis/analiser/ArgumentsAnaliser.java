package linter.type_analysis.analiser;

import java.util.ArrayList;
import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.test_productions.PassedArgumentsProduction;
import linter.syntax_tree.production.test_productions.TestProduction;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Type;

public class ArgumentsAnaliser extends TypeAnaliser {
    
    List<Type> argumentTypes = new ArrayList<Type>();

    protected ArgumentsAnaliser(List<Table<Type>> variableTables, List<Table<Function>> functionTables) {
        super(variableTables, functionTables);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(PassedArgumentsProduction.class))
            return true;
        int position = 0;
        Node child;
        while((child = node.getChildAtPosition(position++)) != null){
            if(child.isType(TestProduction.class)){
                TestAnaliser analiser = new TestAnaliser(variableTables, functionTables);
                child.accept(analiser);
                if(analiser.getType() != null)
                    argumentTypes.add(analiser.getType());
                else if(analiser.hasFunction()){ //a function
                    List<String> identifier = analiser.getIdentifier();
                    //TODO: FIND A FUNCTION AND GET ITS TYPE
                }
                else { //a variable
                    List<String> identifier = analiser.getIdentifier();
                    //TODO: FIND A VARIABLE AND GET ITS TYPE
                }
            }
        }
        return true;
    }

    List<Type> getArguments(){
        return argumentTypes;
    }

}
