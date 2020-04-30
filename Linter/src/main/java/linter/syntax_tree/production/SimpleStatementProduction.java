package linter.syntax_tree.production;

import java.util.Arrays;
import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.token.Token;
import linter.token.type.IdentifierTokenType;
import linter.token.type.SimpleStatementTokenType;

public class SimpleStatementProduction extends Production {

    @Override
    public List<TreeElement> expand(Token token, Token peek) {
        if(token.getTokenType() == IdentifierTokenType.NAME)
            return Arrays.asList(new AssignmentStatementProduction());
        if(token.getTokenType() == SimpleStatementTokenType.PASS
            || token.getTokenType() == SimpleStatementTokenType.CONTINUE
            || token.getTokenType() == SimpleStatementTokenType.BREAK
        ){
            return Arrays.asList(token);
        }
        if(token.getTokenType() == SimpleStatementTokenType.IMPORT)
            return Arrays.asList(new ImportStatementProduction());
        if(token.getTokenType() == SimpleStatementTokenType.RETURN)
            return Arrays.asList(new ReturnStatementProduction());
        return null;
    }
    

}