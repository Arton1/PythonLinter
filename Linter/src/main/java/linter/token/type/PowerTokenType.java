package linter.token.type;

import linter.visitor.Visitor;

public enum PowerTokenType implements TokenType {
    POWER;

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

}