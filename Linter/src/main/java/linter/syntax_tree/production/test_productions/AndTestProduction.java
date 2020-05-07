package linter.syntax_tree.production.test_productions;

import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.syntax_tree.production.Production;
import linter.token.Token;
import linter.token.type.LogicTokenType;

public class AndTestProduction extends Production {

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) {
        if(peek.getTokenType() == LogicTokenType.AND)
            return createExpansion(new NotTestProduction(), new AndTestProduction());
        else
            return createExpansion(new NotTestProduction());
    }
}
