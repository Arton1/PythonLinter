package linter.syntax_tree.production.test_productions;

import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.syntax_tree.production.Production;
import linter.token.Token;

public class AtomicExpressionProduction extends Production {

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) {
        return createExpansion(new AtomicProduction());
    }

    public List<TreeElement> expandOptional(Token token, Token peek, int currentIndentLevel) {
        return createExpansion(new OptionalTrailerProduction());
	}

}
