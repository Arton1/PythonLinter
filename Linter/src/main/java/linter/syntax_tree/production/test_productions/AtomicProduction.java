package linter.syntax_tree.production.test_productions;

import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.syntax_tree.production.Production;
import linter.token.Token;
import linter.token.type.BracketTokenType;
import linter.token.type.IdentifierTokenType;
import linter.token.type.LogicTokenType;
import linter.token.type.NullTokenType;
import linter.token.type.NumberTokenType;

public class AtomicProduction extends Production {

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) {
        if(token.getTokenType() == BracketTokenType.ROUNDED_BEGIN)
            return createExpansion(token, new TestListProduction(), BracketTokenType.ROUNDED_END);
        if(token.getTokenType() == BracketTokenType.SQUARED_BEGIN)
            return createExpansion(token, new TestListProduction(), BracketTokenType.SQUARED_END);
        if(token.getTokenType() == IdentifierTokenType.NAME 
            || token.getTokenType() instanceof NumberTokenType
            || token.getTokenType() == LogicTokenType.TRUE
            || token.getTokenType() == LogicTokenType.FALSE
            || token.getTokenType() == NullTokenType.NULL)
            return createExpansion(token);
        
        return null;
    }

}
