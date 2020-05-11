package linter.syntax_tree.production.compound_productions;

import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.token.Token;
import linter.token.type.BlockTokenType;
import linter.token.type.CompoundStatementTokenType;
import linter.token.type.IdentifierTokenType;

public class FunctionStatementProduction extends CompoundStatementProduction {

    public FunctionStatementProduction(int level) {
        super(level);
    }

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) {
        if(token.getTokenType() == CompoundStatementTokenType.FUN)
            return createExpansion(token, 
                                    IdentifierTokenType.NAME, 
                                    new FunctionParametersProduction(), 
                                    new OptionalReturnHintProduction(), 
                                    BlockTokenType.TWO_DOTS, 
                                    BlockTokenType.NEWLINE, 
                                    new SuiteProduction(level));
        return null;
    }
}
