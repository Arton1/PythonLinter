package linter.type_analysis.analiser;

import java.util.ArrayList;
import java.util.List;

import linter.exception.SemanticsException;
import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.test_productions.AtomicExpressionProduction;
import linter.syntax_tree.production.test_productions.AtomicProduction;
import linter.syntax_tree.production.test_productions.OptionalTrailerProduction;
import linter.token.Token;
import linter.type_analysis.Function;
import linter.type_analysis.NameSpace;
import linter.type_analysis.Type;
import linter.type_analysis.Variable;
import linter.type_analysis.Class;

public class AtomicExpressionAnaliser extends TypeAnaliser {

    public AtomicExpressionAnaliser(List<NameSpace> nameSpaceStack) {
        super(nameSpaceStack);
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
        processData(node, identifier);
        return true;
    }

    private void processAtomicProduction(Node node, List<String> identifier){
        AtomicAnaliser analiser = new AtomicAnaliser(nameSpaceStack);
        node.accept(analiser);
        if(analiser.getType() != null)
            type = analiser.getType();
        else if(analiser.getIdentifier() != null)
            identifier.add(analiser.getIdentifier());
        else
            throw new RuntimeException("Nothing received from analyzer");
    }

    private void processTrailerProduction(Node node, List<String> identifier){
        TrailerAnaliser analiser = new TrailerAnaliser(nameSpaceStack);
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
        if(type != null)
            throw new SemanticsException("Not a function identifier", node.getParent().getSubtreeFirstToken());
        Function function = findFunction(identifier);
        if(function == null)
            throw new SemanticsException("Function undefined", node.getParent().getSubtreeFirstToken());
        if(!function.compareArgumentTypes(arguments))
            throw new SemanticsException("Arguments dont match function call", node.getParent().getSubtreeFirstToken());
        function.incrementNumberOfReferences();
        type = function.getReturnType();
    }

    public void processData(Node node, List<String> identifier){
        if(type != null){
            if(type != Type.CLASS_OBJECT){ //is not a class object
                if(identifier.size() > 1)
                    throw new SemanticsException("Cannot get a variable from a type different than class, " + type , node.getParent().getSubtreeFirstToken());
            }
            else{
                throw new RuntimeException("Unimplemented!");
            }
        }
        else {
            try{
                variable = findVariable(identifier);
            }
            catch(RuntimeException e){
                throw new SemanticsException("Class " + e.getMessage() + " unimplemented", node.getSubtreeFirstToken());
            }
            if(variable == null){
                String variableIdentifier = identifier.remove(identifier.size()-1);
                Class classPossesingTheVariable = null;
                if(identifier.size() > 0){
                    classPossesingTheVariable = findClass(identifier);
                    if(classPossesingTheVariable == null)
                        throw new SemanticsException("Cannot find class", node.getParent().getSubtreeFirstToken());
                }
                Token firstToken = node.getSubtreeFirstToken();
                variable = new Variable(variableIdentifier, classPossesingTheVariable, firstToken.getLine(), firstToken.getColumn());
            }
            else 
                variable.incrementNumberOfReferences();
        }
    }

}
