package linter.syntax_tree.production.compound_productions;

import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.token.Token;
import linter.token.type.BlockTokenType;

public class SuiteProduction extends CompoundStatementProduction {

    public SuiteProduction(int level) {
        super(level+1);
    }

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) {
        if(token.getTokenType() == BlockTokenType.NEWLINE && peek.getTokenType() == BlockTokenType.INDENT)
            return createExpansion(BlockTokenType.NEWLINE, new IndentedStatementProduction(level));
        return null;
    }

    @Override
    public List<TreeElement> expandOptional(Token token, Token peek, int currentIndentLevel) {
        if(token.getTokenType() == BlockTokenType.NEWLINE && peek.getTokenType() == BlockTokenType.INDENT)
            return createExpansion(BlockTokenType.NEWLINE, new IndentedStatementProduction(level));
        return createExpansion(); //Epsilon
    }
}
