package linter.syntax_tree.production;

import java.util.List;

import linter.exception.IndentationException;
import linter.syntax_tree.TreeElement;
import linter.syntax_tree.production.test_productions.ExpressionProduction;
import linter.token.Token;
import linter.token.type.AssignmentTokenType;

public class OptionalAssignmentStatementProduction extends Production {

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) throws IndentationException {
        if(token.getTokenType() == AssignmentTokenType.NORMAL_AS)
            return createExpansion(new AnnualAssignmentStatementProduction());
        else 
            if(token.getTokenType() instanceof AssignmentTokenType)
                return createExpansion(token, new ExpressionProduction());
        return createExpansion(); //Epsilon
    }

    
}