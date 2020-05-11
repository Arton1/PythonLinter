package linter.syntax_tree.production;

import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.syntax_tree.production.import_productions.ImportStatementProduction;
import linter.syntax_tree.production.test_productions.TestProduction;
import linter.token.Token;
import linter.token.type.SimpleStatementTokenType;

public class SimpleStatementProduction extends Production {

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) {
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
        return createExpansion(new TestProduction(), new OptionalAssignmentStatementProduction());
    }
    

}