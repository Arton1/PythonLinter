package linter.token.type;

import linter.visitor.Visitor;

public enum LogicTokenType implements TokenType {
    AND, OR, NOT, TRUE, FALSE;

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

}