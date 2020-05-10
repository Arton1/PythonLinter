package linter.syntax_tree.production.compound_productions;

import java.util.List;

import linter.exception.IndentationException;
import linter.syntax_tree.TreeElement;
import linter.syntax_tree.production.Production;
import linter.token.Token;
import linter.token.type.BlockTokenType;
import linter.token.type.IdentifierTokenType;

public class FunctionArgumentProduction extends Production {

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) throws IndentationException {
        if(token.getTokenType() == IdentifierTokenType.NAME)
            if(peek.getTokenType() == BlockTokenType.TWO_DOTS)
                return createExpansion(token, BlockTokenType.TWO_DOTS, IdentifierTokenType.NAME);
            else
                return createExpansion(token);
        return null;
    }
    
}