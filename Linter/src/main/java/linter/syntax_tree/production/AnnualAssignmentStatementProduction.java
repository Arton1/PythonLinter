package linter.syntax_tree.production;

import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.token.Token;
import linter.token.type.AssignmentTokenType;
import linter.token.type.IdentifierTokenType;
import linter.syntax_tree.production.test_productions.ExpressionProduction;

public class AnnualAssignmentStatementProduction extends Production {

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) {
        if(token.getTokenType() == IdentifierTokenType.NAME)
            return createExpansion(token, AssignmentTokenType.NORMAL_AS, this);
        else
            return createExpansion(new ExpressionProduction());
    }
}