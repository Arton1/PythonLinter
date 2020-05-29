package linter.token.type;

import linter.syntax_tree.TreeElementVisitor;

public enum StringTokenType implements TokenType {
    DOUBLE_QUOTE, SINGLE_QUOTE;

    @Override
    public void accept(TreeElementVisitor visitor){
        visitor.visit(this);
    }
}