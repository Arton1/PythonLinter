package linter.syntax_tree.production;

import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.token.Token;
import linter.token.type.AssignmentTokenType;
import linter.token.type.IdentifierTokenType;

public class AnnualAssignmentStatementProduction extends Production {

    @Override
    public List<TreeElement> expand(Token token, Token peek) {
        if(token.getTokenType() == AssignmentTokenType.NORMAL_AS){
            if(peek.getTokenType() == IdentifierTokenType.NAME)
                return createExpansion(token, peek.getTokenType(), this);
            else
                return createExpansion(token, new ExpressionProduction());
        }
        return null;
    }

}