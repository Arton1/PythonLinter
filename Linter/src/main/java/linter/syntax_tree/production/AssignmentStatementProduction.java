package linter.syntax_tree.production;

import java.util.Arrays;
import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.token.Token;
import linter.token.type.AssignmentTokenType;
import linter.token.type.IdentifierTokenType;

public class AssignmentStatementProduction extends Production {

    @Override
    public List<TreeElement> expand(Token token, Token peek) {
        if(token.getTokenType() == IdentifierTokenType.NAME)
            if(peek.getTokenType() == AssignmentTokenType.NORMAL_AS){
                return Arrays.asList(token, new AnnualAssignmentStatementProduction());
            }
            else 
                return Arrays.asList(token, new AugmentedAssignmentStatementProduction());
        return null;
    }

}