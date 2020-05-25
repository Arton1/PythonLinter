package linter.token.type;

import linter.visitor.TreeElementVisitor;

public enum StringTokenType implements TokenType {
    DOUBLE_QUOTE, SINGLE_QUOTE;

    @Override
    public void accept(TreeElementVisitor visitor){
        visitor.visit(this);
    }
}