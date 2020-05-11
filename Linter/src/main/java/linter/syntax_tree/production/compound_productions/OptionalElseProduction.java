package linter.syntax_tree.production.compound_productions;

import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.token.Token;
import linter.token.type.BlockTokenType;
import linter.token.type.CompoundStatementTokenType;

public class OptionalElseProduction extends CompoundStatementProduction {

    public OptionalElseProduction(int level) {
        super(level);
    }

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) {
        if(level == currentIndentLevel)
            if(token.getTokenType() == CompoundStatementTokenType.ELSE)
                return createExpansion(token, BlockTokenType.TWO_DOTS, BlockTokenType.NEWLINE, new SuiteProduction(level));
        return createExpansion(); //Epsilon
    }

}
