package linter.token.type;

import linter.visitor.Visitor;

public enum NullTokenType implements TokenType {
    NULL;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}