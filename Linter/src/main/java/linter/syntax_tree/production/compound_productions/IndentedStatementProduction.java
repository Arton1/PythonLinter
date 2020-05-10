package linter.syntax_tree.production.compound_productions;

import java.util.List;

import linter.exception.IndentationException;
import linter.syntax_tree.TreeElement;
import linter.syntax_tree.production.SimpleStatementProduction;
import linter.token.Token;
import linter.token.type.CompoundStatementTokenType;

public class IndentedStatementProduction extends CompoundStatementProduction {

    public IndentedStatementProduction(int level) {
        super(level);
    }

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) throws IndentationException {
        if(currentIndentLevel == level){
            if(token.getTokenType() instanceof CompoundStatementTokenType 
                && token.getTokenType() != CompoundStatementTokenType.ELIF 
                && token.getTokenType() != CompoundStatementTokenType.ELSE)
                return createExpansion(new CompoundStatementProduction(level));
            else
                return createExpansion(new SimpleStatementProduction());
        }
        else
            if(currentIndentLevel > level)
                throw new IndentationException(level, currentIndentLevel);
        return null;
    }
}