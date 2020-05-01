package linter.syntax_tree.production;

import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.token.Token;
import linter.token.type.BlockTokenType;
import linter.token.type.CompoundStatementTokenType;
import linter.token.type.IdentifierTokenType;
import linter.token.type.SimpleStatementTokenType;

public class SingleInputProduction extends Production {

    @Override
    public List<TreeElement> expand(Token token, Token peek) {
        if(token.getTokenType() instanceof SimpleStatementTokenType || token.getTokenType() == IdentifierTokenType.NAME)
            return createExpansion(new SimpleStatementProduction(), BlockTokenType.NEWLINE);
        if(token.getTokenType() == BlockTokenType.NEWLINE)
            return createExpansion(token);
        if(token.getTokenType() instanceof CompoundStatementTokenType)
            return createExpansion(new CompoundStatementProduction(), BlockTokenType.NEWLINE);
        return null;
    }
}