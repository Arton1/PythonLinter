package linter.syntax_tree.production;

import java.util.Arrays;
import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.token.Token;
import linter.token.type.IdentifierTokenType;
import linter.token.type.SimpleStatementTokenType;

public class DottedAsNameProduction extends Production {

    @Override
    public List<TreeElement> expand(Token token, Token peek) {
        if(token.getTokenType() == IdentifierTokenType.NAME){
            if(peek.getTokenType() == SimpleStatementTokenType.DOT)
                return Arrays.asList(token, peek.getTokenType(), this);
            else
                if(peek.getTokenType() == SimpleStatementTokenType.AS)
                    return Arrays.asList(token, peek.getTokenType(), IdentifierTokenType.NAME);
                else
                    return Arrays.asList(token);
        }
        return null;
    }
    

}
