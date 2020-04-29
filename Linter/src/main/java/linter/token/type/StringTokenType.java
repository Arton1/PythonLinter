package linter.token.type;

import linter.visitor.Visitor;

public enum StringTokenType implements TokenType {
    DOUBLE_QUOTE, SINGLE_QUOTE;

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }
}