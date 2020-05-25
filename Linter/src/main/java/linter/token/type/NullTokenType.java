package linter.token.type;

import linter.visitor.TreeElementVisitor;

public enum NullTokenType implements TokenType {
    NULL;

    @Override
    public void accept(TreeElementVisitor visitor) {
        visitor.visit(this);
    }
}