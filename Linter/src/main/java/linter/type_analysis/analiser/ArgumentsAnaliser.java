package linter.type_analysis.analiser;

import java.util.ArrayList;
import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.test_productions.PassedArgumentsProduction;
import linter.syntax_tree.production.test_productions.TestProduction;
import linter.type_analysis.Function;
import linter.type_analysis.NameSpace;
import linter.type_analysis.Type;
import linter.type_analysis.Variable;

public class ArgumentsAnaliser extends TypeAnaliser {
    
    List<Type> argumentTypes = new ArrayList<Type>();

    protected ArgumentsAnaliser(List<NameSpace> nameSpaceStack) {
        super(nameSpaceStack);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(PassedArgumentsProduction.class))
            return true;
        int position = 0;
        Node child;
        while((child = node.getChildAtPosition(position++)) != null){
            if(child.isType(TestProduction.class)){
                TestAnaliser analiser = new TestAnaliser(nameSpaceStack);
                child.accept(analiser);
                if(analiser.getType() != null)
                    argumentTypes.add(analiser.getType());
                else if(analiser.getFunction() != null){ //a function
                    Function receivedFunction = analiser.getFunction();
                    argumentTypes.add(receivedFunction.getReturnType());
                }
                else if(analiser.getVariable() != null){ //a variable
                    Variable receivedVariable = analiser.getVariable();
                    argumentTypes.add(receivedVariable.getType());
                }
            }
        }
        return true;
    }

    List<Type> getArguments(){
        return argumentTypes;
    }

}
