package linter.syntax_tree.production;

import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.token.Token;
import linter.token.type.BlockTokenType;
import linter.token.type.SimpleStatementTokenType;

public class ReturnStatementProduction extends Production {

    @Override
    public List<TreeElement> expand(Token token, Token peek) {
        if(token.getTokenType() == SimpleStatementTokenType.RETURN){
            if(peek.getTokenType() == BlockTokenType.NEWLINE)
                return createExpansion(token);
            else
                return createExpansion(token, new TestProdution());
        }
        return null;
    }

}