package linter.syntax_tree.production;

import java.util.Arrays;
import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.token.Token;
import linter.token.type.AssignmentTokenType;

public class AugmentedAssignmentStatementProduction extends Production {

    @Override
    public List<TreeElement> expand(Token token, Token peek) {
        if(token.getTokenType() instanceof AssignmentTokenType)
            return Arrays.asList(token, new ExpressionProduction());
        return null;
    }
    

}
