package linter.syntax_tree.production.test_productions;

import java.util.List;

import linter.exception.IndentationException;
import linter.syntax_tree.TreeElement;
import linter.syntax_tree.production.Production;
import linter.token.Token;
import linter.token.type.AssignmentTokenType;

public class PassedArgumentsProduction extends Production {
    private boolean expandedOptional = false;

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) throws IndentationException {
        return createExpansion(new TestProduction());
    }

    @Override
    public List<TreeElement> expandOptional(Token token, Token peek, int currentIndentLevel) {
        if(!expandedOptional)
            if(token.getTokenType() == AssignmentTokenType.NORMAL_AS){
                this.expandedOptional = true;
                return createExpansion(token, new TestProduction());
            }
        return null; //by default
	}

}
