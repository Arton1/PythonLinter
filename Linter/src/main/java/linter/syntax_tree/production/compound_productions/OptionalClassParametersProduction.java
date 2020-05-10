package linter.syntax_tree.production.compound_productions;

import java.util.List;

import linter.exception.IndentationException;
import linter.syntax_tree.TreeElement;
import linter.syntax_tree.production.Production;
import linter.token.Token;
import linter.token.type.BracketTokenType;

public class OptionalClassParametersProduction extends Production {

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) throws IndentationException {
        if(token.getTokenType() == BracketTokenType.ROUNDED_BEGIN)
            if(peek.getTokenType() == BracketTokenType.ROUNDED_END)
                createExpansion(token, BracketTokenType.ROUNDED_END);
            else
                return createExpansion(token, new ClassArgumentsProduction(), BracketTokenType.ROUNDED_END);
        return createExpansion(); //Epsilon
    }
    
}
