package linter.syntax_tree.production;

import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.syntax_tree.production.test_productions.ExpressionProduction;
import linter.token.Token;
import linter.token.type.AssignmentTokenType;
import linter.token.type.IdentifierTokenType;

public class AssignmentStatementProduction extends Production {

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) {
        if(token.getTokenType() == IdentifierTokenType.NAME)
            if(peek.getTokenType() == AssignmentTokenType.NORMAL_AS)
                return createExpansion(new AnnualAssignmentStatementProduction());
            else 
                if(peek.getTokenType() instanceof AssignmentTokenType)
                    return createExpansion(token, peek.getTokenType(), new ExpressionProduction());
        return null;
    }
}