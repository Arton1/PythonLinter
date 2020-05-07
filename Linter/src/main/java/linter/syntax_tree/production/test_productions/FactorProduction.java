package linter.syntax_tree.production.test_productions;

import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.syntax_tree.production.Production;
import linter.token.Token;
import linter.token.type.SignTokenType;

public class FactorProduction extends Production {

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) {
        if(token.getTokenType() instanceof SignTokenType)
            return createExpansion(token, new FactorProduction());
        else
            return createExpansion(new PowerProduction());
    }

}
