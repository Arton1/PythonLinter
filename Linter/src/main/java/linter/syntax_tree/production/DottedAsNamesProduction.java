package linter.syntax_tree.production;

import java.util.Arrays;
import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.token.Token;
import linter.token.type.SimpleStatementTokenType;

public class DottedAsNamesProduction extends Production {

    @Override
    public List<TreeElement> expand(Token token, Token peek) {
        if(peek.getTokenType() == SimpleStatementTokenType.COMMA){
            return Arrays.asList(new DottedAsNameProduction(), peek.getTokenType(), this);
        }
        else
            return Arrays.asList(new DottedAsNameProduction());
    }

}
