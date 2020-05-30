package linter.type_analysis.analiser.CompoundAnalizer;

import java.util.ArrayList;
import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.TokenNode;
import linter.syntax_tree.production.compound_productions.FunctionParametersProduction;
import linter.syntax_tree.production.compound_productions.FunctionStatementProduction;
import linter.syntax_tree.production.compound_productions.OptionalReturnHintProduction;
import linter.syntax_tree.production.compound_productions.ReturnHintAnalizer;
import linter.syntax_tree.production.compound_productions.SuiteProduction;
import linter.token.IdentifierToken;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Type;
import linter.type_analysis.Variable;

public class FunctionStatementAnalizer extends CompoundAnalizer {
    String functionIdentifier;
    Type returnType = null;
    List<Variable> arguments = null;

    public FunctionStatementAnalizer(List<Table<Variable>> variableTables, List<Table<Function>> functionTables, List<Table<Variable>> retiredVariableTables, List<Table<Function>> retiredFunctionTables) {
        super(variableTables, functionTables, retiredVariableTables, retiredFunctionTables);
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
        if(node.getToken() instanceof IdentifierToken)
            functionIdentifier = ((IdentifierToken)node.getToken()).getIdentifier();
    }

    private void processFunctionParameters(Node node) {
        FunctionParametersAnalizer analizer = new FunctionParametersAnalizer(variableTables, functionTables);
        node.accept(analizer);
        arguments = analizer.getArguments();
    }

    private void processReturnType(Node node) {
        ReturnHintAnalizer analizer = new ReturnHintAnalizer(variableTables, functionTables);
        node.accept(analizer);
        returnType = analizer.getReturnType();
    }

    private void processSuiteProduction(Node node) {
        variableTables.add(new Table<Variable>());
        createFunctionObject(); //add function to previous space, add variables to new space
        functionTables.add(new Table<Function>());
        SuiteAnalizer analizer = new SuiteAnalizer(variableTables, functionTables, retiredVariableTables, retiredFunctionTables);
        node.accept(analizer);
    }

    private void createFunctionObject(){
        List<Type> argumentTypes = new ArrayList<Type>();
        for(Variable argument : arguments){
            saveVariable(argument);
            argumentTypes.add(argument.getType());
        }
        if(returnType == null)
            returnType = Type.UNSPECIFIED;
        Function function = new Function(functionIdentifier, returnType, argumentTypes);
        saveFunction(function);
    }
    
}