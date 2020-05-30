package linter.type_analysis.analiser.CompoundAnalizer;

import java.util.List;

import linter.exception.SemanticsException;
import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.TokenNode;
import linter.syntax_tree.production.compound_productions.ForStatementProduction;
import linter.syntax_tree.production.compound_productions.SuiteProduction;
import linter.syntax_tree.production.test_productions.AtomicExpressionProduction;
import linter.token.IdentifierToken;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Type;
import linter.type_analysis.Variable;
import linter.type_analysis.analiser.AtomicExpressionAnaliser;

public class ForStatementAnalizer extends CompoundAnalizer {

    public ForStatementAnalizer(List<Table<Variable>> variableTables, List<Table<Function>> functionTables, List<Table<Variable>> retiredVariableTables, List<Table<Function>> retiredFunctionTables) {
        super(variableTables, functionTables, retiredVariableTables, retiredFunctionTables);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(ForStatementProduction.class))
            return true;
        int position = 0;
        Node child;
        variableTables.add(new Table<Variable>());
        functionTables.add(new Table<Function>());
        while((child = node.getChildAtPosition(position++)) != null){
            if(child.isType(AtomicExpressionProduction.class))
                processAtomicExpressionProduction(child);
            else if(child.isType(SuiteProduction.class))
                processSuiteProduction(child);
            else if(child instanceof TokenNode)
                child.accept(this);
        }
        return true;
    }

    private void processAtomicExpressionProduction(Node child) {
        AtomicExpressionAnaliser analiser = new AtomicExpressionAnaliser(variableTables, functionTables);
        child.accept(analiser);
        Type type = null;
        if(analiser.getType() != null)
            type = analiser.getType();
        else if(analiser.getVariable() != null){
            if(analiser.getVariable().getType() == null)
                throw new SemanticsException("Uninitialized variable", child.getSubtreeFirstToken());
            type = analiser.getVariable().getType();
        }
        if(type != Type.LIST && type != Type.TUPLE && type != Type.UNSPECIFIED)
            throw new SemanticsException("Expected list or tuple, got " + type, child.getSubtreeFirstToken());
        
    }
    
    private void processSuiteProduction(Node node) {
        SuiteAnalizer analizer = new SuiteAnalizer(variableTables, functionTables, retiredVariableTables, retiredFunctionTables);
        node.accept(analizer);
    }

    @Override
    public void visit(TokenNode node) {
        if(node.getToken() instanceof IdentifierToken){
            String identifier = ((IdentifierToken)node.getToken()).getIdentifier();
            Variable variable = new Variable(identifier);
            variable.setType(Type.UNSPECIFIED);
            saveVariable(variable);
        }
    }
}