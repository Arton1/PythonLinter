package linter.type_analysis.analiser;

import java.util.ArrayList;
import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.TokenNode;
import linter.syntax_tree.production.test_productions.OptionalTrailerProduction;
import linter.syntax_tree.production.test_productions.PassedArgumentsProduction;
import linter.token.IdentifierToken;
import linter.token.type.BracketTokenType;
import linter.type_analysis.NameSpace;
import linter.type_analysis.Type;

public class TrailerAnaliser extends TypeAnaliser {

    List<Type> arguments = null;
    String identifier = null;

    protected TrailerAnaliser(List<NameSpace> nameSpaceStack) {
        super(nameSpaceStack);
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
        ArgumentsAnaliser analiser = new ArgumentsAnaliser(nameSpaceStack);
        node.accept(analiser);
        arguments = analiser.getArguments();
    }

    @Override
    public void visit(TokenNode node) {
        if(node.getToken() instanceof IdentifierToken)
           identifier = ((IdentifierToken)node.getToken()).getIdentifier();
        else if(node.getToken().getTokenType() == BracketTokenType.ROUNDED_END && arguments == null)
            arguments = new ArrayList<Type>(); //empty list of arguments
    }

    public String getIdentifier(){
        return identifier;
    }

	public List<Type> getArguments() {
		return arguments;
	}

}
