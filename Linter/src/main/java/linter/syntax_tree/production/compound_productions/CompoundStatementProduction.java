package linter.syntax_tree.production.compound_productions;

import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.syntax_tree.production.Production;
import linter.token.Token;
import linter.token.type.CompoundStatementTokenType;

public class CompoundStatementProduction extends Production {
    protected int level;

    public CompoundStatementProduction(int level){
        this.level = level;
    }

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) {
        if(token.getTokenType() == CompoundStatementTokenType.IF)
            return createExpansion(new IfStatementProduction(level));
        if(token.getTokenType() == CompoundStatementTokenType.WHILE)
            return createExpansion(new WhileStatementProduction(level));
        if(token.getTokenType() == CompoundStatementTokenType.FOR)
            return createExpansion(new ForStatementProduction(level));
        if(token.getTokenType() == CompoundStatementTokenType.CLASS)
            return createExpansion(new ClassStatementProduction(level));
        if(token.getTokenType() == CompoundStatementTokenType.FUN)
            return createExpansion(new FunctionStatementProduction(level));
        return null;
    }

}