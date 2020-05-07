package linter.syntax_tree.production.test_productions;

import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.syntax_tree.production.Production;
import linter.token.Token;
import linter.token.type.CompareTokenType;

public class ComparisonProduction extends Production {

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) {
        return createExpansion(new ExpressionProduction());
    }
    
    public List<TreeElement> expandOptional(Token token, Token peek, int currentIndentLevel) {
		if(token.getTokenType() instanceof CompareTokenType)
            return createExpansion(token, new ExpressionProduction());
        return null;
	}
    
}
