package linter.syntax_tree.production.compound_productions;

import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.syntax_tree.production.test_productions.TestProduction;
import linter.token.Token;
import linter.token.type.BlockTokenType;
import linter.token.type.CompoundStatementTokenType;

public class OptionalElifsProduction extends CompoundStatementProduction {

    public OptionalElifsProduction(int level) {
        super(level);
    }

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) {
        if(token.getTokenType() == CompoundStatementTokenType.ELIF)
            return createExpansion(token, new TestProduction(), BlockTokenType.TWO_DOTS, new SuiteProduction(level));
        return createExpansion(); //Epsilon
    }
    
    public List<TreeElement> expandOptional(Token token, Token peek, int currentIndentLevel) {
        if(token.getTokenType() == CompoundStatementTokenType.ELIF)
            createExpansion(token, new TestProduction(), BlockTokenType.TWO_DOTS, new SuiteProduction(level));
        return null; //by default
	}
}
