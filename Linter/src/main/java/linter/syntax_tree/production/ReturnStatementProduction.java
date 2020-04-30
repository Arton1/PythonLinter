package linter.syntax_tree.production;

import java.util.Arrays;
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
                return Arrays.asList(token);
            else
                return Arrays.asList(token, new TestProdution());
        }
        return null;
    }

}