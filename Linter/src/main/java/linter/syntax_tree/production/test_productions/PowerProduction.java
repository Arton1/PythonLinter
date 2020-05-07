package linter.syntax_tree.production.test_productions;

import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.syntax_tree.production.Production;
import linter.token.Token;
import linter.token.type.PowerTokenType;

public class PowerProduction extends Production {
    boolean addedOptional = false;

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) {
        return createExpansion(new AtomicExpressionProduction());
    }

    public List<TreeElement> expandOptional(Token token, Token peek, int currentIndentLevel) {
		if(!addedOptional && token.getTokenType() == PowerTokenType.POWER){
            addedOptional = true;
            return createExpansion(token, new FactorProduction());
        }
        return null;
	}

}
