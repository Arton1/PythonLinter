package linter.syntax_tree.production;

import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.token.Token;
import linter.token.type.BlockTokenType;
import linter.token.type.CompoundStatementTokenType;

import linter.syntax_tree.production.compound_productions.CompoundStatementProduction;

public class SingleInputProduction extends Production {

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) {
        if(token.getTokenType() == BlockTokenType.NEWLINE)
            return createExpansion(token);
        if(token.getTokenType() instanceof CompoundStatementTokenType)
            return createExpansion(new CompoundStatementProduction(0));
        return createExpansion(new SimpleStatementProduction(), BlockTokenType.NEWLINE);
    }
}