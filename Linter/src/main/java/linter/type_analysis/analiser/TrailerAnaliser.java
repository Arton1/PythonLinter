package linter.type_analysis.analiser;

import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.TokenNode;
import linter.syntax_tree.production.test_productions.OptionalTrailerProduction;
import linter.syntax_tree.production.test_productions.PassedArgumentsProduction;
import linter.token.IdentifierToken;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Type;
import linter.type_analysis.Variable;

public class TrailerAnaliser extends TypeAnaliser {

    List<Type> arguments = null;
    String identifier = null;

    protected TrailerAnaliser(List<Table<Variable>> variableTables, List<Table<Function>> functionTables) {
        super(variableTables, functionTables);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(OptionalTrailerProduction.class))
            return true;
        int position = 0;
        Node child;
        while((child = node.getChildAtPosition(position++)) != null){
            if(child.isType(PassedArgumentsProduction.class))
                processPassedArguments(child);
            else if(child instanceof TokenNode)
                child.accept(this);
        }
        return true;
    }

    private void processPassedArguments(Node node){
        ArgumentsAnaliser analiser = new ArgumentsAnaliser(variableTables, functionTables);
        node.accept(analiser);
        arguments = analiser.getArguments();
    }

    @Override
    public void visit(TokenNode node) {
        if(node.getToken() instanceof IdentifierToken)
           identifier = ((IdentifierToken)node.getToken()).getIdentifier();
    }

    public String getIdentifier(){
        return identifier;
    }

	public List<Type> getArguments() {
		return arguments;
	}

}
