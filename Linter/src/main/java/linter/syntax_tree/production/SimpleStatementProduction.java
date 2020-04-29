package linter.syntax_tree.production;

import java.util.Arrays;
import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.token.Token;
import linter.token.type.IdentifierTokenType;
import linter.token.type.SimpleStatementTokenType;

public class SimpleStatementProduction extends Production {

    @Override
    public List<TreeElement> expand(Token token) {
        if(token.getTokenType() == IdentifierTokenType.NAME)
            return Arrays.asList(new AssignmentStatementProduction());
        if(token.getTokenType() == SimpleStatementTokenType.PASS)
            return Arrays.asList(token);
        return null;
    }
    

}