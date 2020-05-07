package linter.syntax_tree.production.compound_productions;

import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.syntax_tree.production.SimpleStatementProduction;
import linter.token.Token;
import linter.token.type.BlockTokenType;
import linter.token.type.CompoundStatementTokenType;

public class SuiteProduction extends CompoundStatementProduction {

    public SuiteProduction(int level) {
        super(level+1);
    }

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) {
        if(currentIndentLevel == level){
            if(token.getTokenType() instanceof CompoundStatementTokenType && token.getTokenType() != CompoundStatementTokenType.ELIF && token.getTokenType() != CompoundStatementTokenType.ELSE)
                return createExpansion(BlockTokenType.NEWLINE, new CompoundStatementProduction(level), new SuiteProduction(level));
            else
                return createExpansion(BlockTokenType.NEWLINE, new SimpleStatementProduction(), new SuiteProduction(level));
        }
        else
            return createExpansion(); //Epsilon
    }
}
