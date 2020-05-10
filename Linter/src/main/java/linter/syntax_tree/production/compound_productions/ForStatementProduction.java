package linter.syntax_tree.production.compound_productions;

import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.syntax_tree.production.test_productions.AtomicExpressionProduction;
import linter.token.Token;
import linter.token.type.BlockTokenType;
import linter.token.type.CompareTokenType;
import linter.token.type.CompoundStatementTokenType;

public class ForStatementProduction extends CompoundStatementProduction{

    public ForStatementProduction(int level) {
        super(level);
    }

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) {
        if (token.getTokenType() == CompoundStatementTokenType.FOR)
            return createExpansion(token, 
                                    new NamesListProduction(), 
                                    CompareTokenType.IN, 
                                    new AtomicExpressionProduction(), 
                                    BlockTokenType.TWO_DOTS, 
                                    new SuiteProduction(level));
        return null;
    }
}
