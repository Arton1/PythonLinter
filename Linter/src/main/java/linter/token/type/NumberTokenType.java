package linter.token.type;

import linter.visitor.Visitor;

public enum NumberTokenType implements TokenType{
    INTEGER, DOUBLE;

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

}