package linter.type_analysis.analiser.CompoundAnalizer;

import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.TokenNode;
import linter.syntax_tree.production.compound_productions.FunctionParametersProduction;
import linter.syntax_tree.production.compound_productions.FunctionStatementProduction;
import linter.syntax_tree.production.compound_productions.OptionalReturnHintProduction;
import linter.syntax_tree.production.compound_productions.SuiteProduction;
import linter.token.IdentifierToken;
import linter.type_analysis.Function;
import linter.type_analysis.NameSpace;
import linter.type_analysis.Type;
import linter.type_analysis.Variable;

public class FunctionStatementAnalizer extends CompoundAnalizer {
    String functionIdentifier;
    Type returnType = Type.UNSPECIFIED;
    List<Variable> arguments = null;
    int line;
    int column;

    public FunctionStatementAnalizer(List<NameSpace> nameSpaceStack, List<NameSpace> retiredNameSpaces, NameSpace currentContextNameSpace) {
        super(nameSpaceStack, retiredNameSpaces, currentContextNameSpace, null);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(FunctionStatementProduction.class))
            return true;
        int position = 0;
        Node child;
        while((child = node.getChildAtPosition(position++)) != null){
            if(child.isType(FunctionParametersProduction.class))
                processFunctionParameters(child);
            else if (child.isType(OptionalReturnHintProduction.class))
                processReturnType(child);
            else if (child.isType(SuiteProduction.class))
                processSuiteProduction(child);
            else if (child instanceof TokenNode)
                child.accept(this);
        }
        return true;
    }

    @Override
    public void visit(TokenNode node){
        if(node.getToken() instanceof IdentifierToken){
            IdentifierToken token = (IdentifierToken)node.getToken();
            functionIdentifier = token.getIdentifier();
            line = token.getLine();
            column = token.getColumn();
        }
    }

    private void processFunctionParameters(Node node) {
        FunctionParametersAnalizer analizer = new FunctionParametersAnalizer(nameSpaceStack);
        node.accept(analizer);
        arguments = analizer.getArguments();
    }

    private void processReturnType(Node node) {
        ReturnHintAnalizer analizer = new ReturnHintAnalizer(nameSpaceStack);
        node.accept(analizer);
        returnType = analizer.getReturnType();
    }

    private void processSuiteProduction(Node node) {
        createFunctionObject(); //add function to previous space, add variables to new space
        SuiteAnalizer analizer = new SuiteAnalizer(nameSpaceStack, retiredNameSpaces, currentContextNameSpace, returnType);
        node.accept(analizer);
        removeCurrentNameSpace();
    }

    private void createFunctionObject(){
        Function function = new Function(functionIdentifier, returnType, arguments, line, column);
        saveFunction(function); //add to old name space
        addNewNameSpace();
        for(Variable argument : arguments)
            saveVariable(argument); //add to new name space
    }
    
}