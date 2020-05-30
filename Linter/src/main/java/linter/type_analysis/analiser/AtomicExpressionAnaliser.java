package linter.type_analysis.analiser;

import java.util.ArrayList;
import java.util.List;

import linter.exception.SemanticsException;
import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.test_productions.AtomicExpressionProduction;
import linter.syntax_tree.production.test_productions.AtomicProduction;
import linter.syntax_tree.production.test_productions.OptionalTrailerProduction;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Type;
import linter.type_analysis.Variable;

public class AtomicExpressionAnaliser extends TypeAnaliser {

    public AtomicExpressionAnaliser(List<Table<Variable>> variableTables, List<Table<Function>> functionTables) {
        super(variableTables, functionTables);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(AtomicExpressionProduction.class))
            return true;
        int position = 0;
        Node child;
        List<String> identifier = new ArrayList<String>();
        while((child = node.getChildAtPosition(position++)) != null){
            if(child.isType(AtomicProduction.class))
                processAtomicProduction(child, identifier);
            else
                if(child.isType(OptionalTrailerProduction.class))
                    processTrailerProduction(child, identifier);
        }
        if(type != null){
            if(type.getLabel() != null){ //is not a class object
                if(identifier.size() != 0)
                    throw new SemanticsException("Cannot get a variable from a type different than class, " + type , node.getParent().getSubtreeFirstToken());
            }
            else{
                throw new RuntimeException("Unimplemented!");
            }
        }
        else {
            variable = findVariable(identifier);
            if(variable == null)
                variable = new Variable(identifier);
            else {
                variable.incrementNumberOfReferences();
            }
        }
        return true;
    }

    private void processAtomicProduction(Node node, List<String> identifier){
        AtomicAnaliser analiser = new AtomicAnaliser(variableTables, functionTables);
        node.accept(analiser);
        if(analiser.getType() != null)
            type = analiser.getType();
        else if(analiser.getIdentifier() != null)
            identifier.add(analiser.getIdentifier());
        else
            throw new RuntimeException("Nothing received from analyzer");
    }

    private void processTrailerProduction(Node node, List<String> identifier){
        TrailerAnaliser analiser = new TrailerAnaliser(variableTables, functionTables);
        node.accept(analiser);
        if(analiser.getArguments() != null){
           processFunctionCall(node, identifier, analiser.getArguments());
           return;
        }
        if(analiser.getIdentifier() != null){
            identifier.add(analiser.getIdentifier());
            return;
        }
        throw new RuntimeException("Nothing received from analyzer");
        
    }

    public void processFunctionCall(Node node, List<String> identifier, List<Type> arguments){
        Function function = findFunction(identifier);
        if(function == null)
            throw new SemanticsException("Function undefined", node.getParent().getSubtreeFirstToken());
        if(!function.compareArgumentTypes(arguments))
            throw new SemanticsException("Arguments dont match for function call", node.getParent().getSubtreeFirstToken());
        function.incrementNumberOfReferences();
        type = function.getReturnType();
    }

}
