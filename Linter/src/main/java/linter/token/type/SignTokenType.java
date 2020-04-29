package linter.token.type;

import linter.visitor.Visitor;

public enum SignTokenType implements TokenType {
    PLUS, MINUS, NEGATION;

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

}