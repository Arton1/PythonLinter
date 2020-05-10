package linter.syntax_tree.production;

import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.syntax_tree.production.test_productions.TestProduction;
import linter.token.Token;
import linter.token.type.BlockTokenType;
import linter.token.type.SimpleStatementTokenType;

public class ReturnStatementProduction extends Production {

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) {
        if(token.getTokenType() == SimpleStatementTokenType.RETURN){
            if(peek.getTokenType() == BlockTokenType.NEWLINE)
                return createExpansion(token);
            else
                return createExpansion(token, new TestProduction());
        }
        return null;
    }

}