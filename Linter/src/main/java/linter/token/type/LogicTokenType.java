package linter.token.type;

import linter.visitor.Visitor;

public enum LogicTokenType implements TokenType {
    AND, OR, NOT;

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

}