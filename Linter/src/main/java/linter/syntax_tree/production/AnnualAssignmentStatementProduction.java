package linter.syntax_tree.production;

import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.token.Token;
import linter.token.type.AssignmentTokenType;
import linter.syntax_tree.production.test_productions.ExpressionProduction;

public class AnnualAssignmentStatementProduction extends Production {
    boolean finishedExpanding = false;

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) {
        if(token.getTokenType() == AssignmentTokenType.NORMAL_AS)
            return createExpansion(token, new ExpressionProduction());
        return null;
    }

    @Override
    public List<TreeElement> expandOptional(Token token, Token peek, int currentIndentLevel) {
        if(!finishedExpanding){
            if(token.getTokenType() == AssignmentTokenType.NORMAL_AS)
                return createExpansion(token, new ExpressionProduction());
            finishedExpanding = true;
        }
        return null;
	}
}