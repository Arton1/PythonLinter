package linter.syntax_tree.production.compound_productions;

import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.token.Token;
import linter.token.type.BlockTokenType;
import linter.token.type.CompoundStatementTokenType;
import linter.syntax_tree.production.test_productions.TestProduction;

public class WhileStatementProduction extends CompoundStatementProduction{

    public WhileStatementProduction(int level) {
        super(level);
    }

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) {
        if(token.getTokenType() == CompoundStatementTokenType.WHILE)
            return createExpansion(token, 
                                    new TestProduction(), 
                                    BlockTokenType.TWO_DOTS, 
                                    BlockTokenType.NEWLINE, 
                                    new SuiteProduction(level), 
                                    new OptionalElseProduction(level));
        return null;
    }

}
