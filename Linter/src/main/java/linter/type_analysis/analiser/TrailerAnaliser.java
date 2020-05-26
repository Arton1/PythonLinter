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

public class TrailerAnaliser extends TypeAnaliser {

    List<Type> arguments = null;

    protected TrailerAnaliser(List<Table<Type>> variableTables, List<Table<Function>> functionTables) {
        super(variableTables, functionTables);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(OptionalTrailerProduction.class))
            return true;
        int position = 0;
        Node child;
        while((child = node.getChildAtPosition(position++)) != null){
            if(child.isType(PassedArgumentsProduction.class)){
                ArgumentsAnaliser analiser = new ArgumentsAnaliser(variableTables, functionTables);
                child.accept(analiser);
            }
            else if(child instanceof TokenNode)
                child.accept(this);
        }
        return true;
    }

    @Override
    public void visit(TokenNode node) {
        if(node.getToken() instanceof IdentifierToken)
            addIdentifier(((IdentifierToken)node.getToken()).getIdentifier());
    }

	public List<Type> getArguments() {
		return arguments;
	}

}
