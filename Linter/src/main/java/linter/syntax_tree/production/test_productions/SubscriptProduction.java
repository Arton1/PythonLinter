package linter.syntax_tree.production.test_productions;

import java.util.List;

import linter.exception.IndentationException;
import linter.syntax_tree.TreeElement;
import linter.syntax_tree.production.Production;
import linter.token.Token;
import linter.token.type.BlockTokenType;

public class SubscriptProduction extends Production {
    private int amountOfExpansions = 0;

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) throws IndentationException {
        return createExpansion(new ExpressionProduction());
    }

    @Override
    public List<TreeElement> expandOptional(Token token, Token peek, int currentIndentLevel) {
        if(amountOfExpansions < 2)
            if(token.getTokenType() == BlockTokenType.TWO_DOTS){
                amountOfExpansions++;
                return createExpansion(token, new ExpressionProduction());
            }
        return null;
	}
}
