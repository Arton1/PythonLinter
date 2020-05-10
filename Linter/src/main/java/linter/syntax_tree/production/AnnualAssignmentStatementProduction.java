package linter.syntax_tree.production;

import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.token.Token;
import linter.token.type.AssignmentTokenType;
import linter.token.type.IdentifierTokenType;
import linter.syntax_tree.production.test_productions.ExpressionProduction;

public class AnnualAssignmentStatementProduction extends Production {
    boolean finishedExpanding = false;

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) {
        if(token.getTokenType() == IdentifierTokenType.NAME)
            if(peek.getTokenType() == AssignmentTokenType.NORMAL_AS)
                return createExpansion(token, AssignmentTokenType.NORMAL_AS);
        return null;
    }

    @Override
    public List<TreeElement> expandOptional(Token token, Token peek, int currentIndentLevel) {
        if(!finishedExpanding){
            if(token.getTokenType() == IdentifierTokenType.NAME)
                if(peek.getTokenType() == AssignmentTokenType.NORMAL_AS)
                    return createExpansion(token, AssignmentTokenType.NORMAL_AS);
            finishedExpanding = true;
            return createExpansion(new ExpressionProduction());
        }
        return null;
	}
}