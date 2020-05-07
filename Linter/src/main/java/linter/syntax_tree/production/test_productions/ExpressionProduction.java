package linter.syntax_tree.production.test_productions;

import java.util.List;

import linter.syntax_tree.production.Production;
import linter.syntax_tree.TreeElement;
import linter.token.Token;
import linter.token.type.SignTokenType;

public class ExpressionProduction extends Production {

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) {
        return createExpansion(new TermProduction());
    }

    public List<TreeElement> expandOptional(Token token, Token peek, int currentIndentLevel) {
		if(token.getTokenType() == SignTokenType.MINUS || token.getTokenType() == SignTokenType.PLUS)
            return createExpansion(token, new TermProduction());
        return null;
	}
}
