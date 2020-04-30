package linter.syntax_tree.production;

import java.util.Arrays;
import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.token.Token;
import linter.token.type.SimpleStatementTokenType;

public class ImportStatementProduction extends Production {

    @Override
    public List<TreeElement> expand(Token token, Token peek) {
        if(token.getTokenType() == SimpleStatementTokenType.RETURN)
            return Arrays.asList(token, new DottedAsNamesProduction());
        //else if(token.getTokenType() == SimpleStatementTokenType.FROM) //TODO: IMPLEMENT FROM
        return null;
    }

}