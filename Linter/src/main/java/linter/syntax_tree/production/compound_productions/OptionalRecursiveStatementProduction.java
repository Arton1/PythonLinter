package linter.syntax_tree.production.compound_productions;

import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.token.Token;

public class OptionalRecursiveStatementProduction extends CompoundStatementProduction {

    public OptionalRecursiveStatementProduction(int level) {
        super(level);
    }

    @Override
    public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) {
        return createExpansion();
    }
    
}