package linter.syntax_tree.production.test_productions;

import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.syntax_tree.production.Production;
import linter.token.Token;
import linter.token.type.BracketTokenType;
import linter.token.type.IdentifierTokenType;
import linter.token.type.SimpleStatementTokenType;

public class OptionalTrailerProduction extends Production {

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) {
        if(token.getTokenType() == BracketTokenType.ROUNDED_BEGIN)
            if(peek.getTokenType() == BracketTokenType.ROUNDED_END)
                return createExpansion(token, BracketTokenType.ROUNDED_END);
            else
                return createExpansion(token, new PassedArgumentsProduction(), BracketTokenType.ROUNDED_END);
        if(token.getTokenType() == BracketTokenType.SQUARED_BEGIN)
            if(peek.getTokenType() == BracketTokenType.SQUARED_END)
                return createExpansion(token, BracketTokenType.SQUARED_END);
            else
                return createExpansion(token, new SubscriptProduction(), BracketTokenType.SQUARED_END);
        if(token.getTokenType() == SimpleStatementTokenType.DOT)
            return createExpansion(token, IdentifierTokenType.NAME);
        else
            return createExpansion();
    }

}
