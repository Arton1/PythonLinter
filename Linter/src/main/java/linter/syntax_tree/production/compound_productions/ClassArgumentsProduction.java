package linter.syntax_tree.production.compound_productions;

import java.util.List;

import linter.exception.IndentationException;
import linter.syntax_tree.TreeElement;
import linter.syntax_tree.production.Production;
import linter.token.Token;
import linter.token.type.IdentifierTokenType;
import linter.token.type.SimpleStatementTokenType;

public class ClassArgumentsProduction extends Production {

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) throws IndentationException {
        if(token.getTokenType() == IdentifierTokenType.NAME)
            return createExpansion(token);
        return null;
    }

    public List<TreeElement> expandOptional(Token token, Token peek, int currentIndentLevel) {
        if(token.getTokenType() == SimpleStatementTokenType.COMMA)
            return createExpansion(token, IdentifierTokenType.NAME);
        return null; 
	}

}
