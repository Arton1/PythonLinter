package linter.syntax_tree.production.test_productions;

import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.syntax_tree.production.Production;
import linter.token.Token;
import linter.token.type.LogicTokenType;

public class TestProduction extends Production {

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) {
        return createExpansion(new AndTestProduction());
    }

    public List<TreeElement> expandOptional(Token token, Token peek, int currentIndentLevel) {
		if(token.getTokenType() == LogicTokenType.OR)
            return createExpansion(token, new AndTestProduction());
        return null;
	}
}
