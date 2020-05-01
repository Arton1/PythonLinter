package linter.syntax_tree.production;

import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.syntax_tree.production.import_productions.ImportStatementProduction;
import linter.token.Token;
import linter.token.type.IdentifierTokenType;
import linter.token.type.SimpleStatementTokenType;

public class SimpleStatementProduction extends Production {

    @Override
    public List<TreeElement> expand(Token token, Token peek) {
        if(token.getTokenType() == IdentifierTokenType.NAME)
            return createExpansion(new AssignmentStatementProduction());
        if(token.getTokenType() == SimpleStatementTokenType.PASS
            || token.getTokenType() == SimpleStatementTokenType.CONTINUE
            || token.getTokenType() == SimpleStatementTokenType.BREAK
        ){
            return createExpansion(token);
        }
        if(token.getTokenType() == SimpleStatementTokenType.IMPORT || token.getTokenType() == SimpleStatementTokenType.FROM)
            return createExpansion(new ImportStatementProduction());
        if(token.getTokenType() == SimpleStatementTokenType.RETURN)
            return createExpansion(new ReturnStatementProduction());
        return null;
    }
    

}