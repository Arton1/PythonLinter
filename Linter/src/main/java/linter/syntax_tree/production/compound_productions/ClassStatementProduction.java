package linter.syntax_tree.production.compound_productions;

import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.token.Token;
import linter.token.type.BlockTokenType;
import linter.token.type.CompoundStatementTokenType;
import linter.token.type.IdentifierTokenType;

public class ClassStatementProduction extends CompoundStatementProduction {
    public ClassStatementProduction(int level) {
        super(level);
    }

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) {
        if(token.getTokenType() == CompoundStatementTokenType.CLASS)
            return createExpansion(token, 
                                    IdentifierTokenType.NAME, 
                                    new OptionalClassParametersProduction(), 
                                    BlockTokenType.TWO_DOTS, 
                                    BlockTokenType.NEWLINE, 
                                    new SuiteProduction(level));
        return null;
    }
}
