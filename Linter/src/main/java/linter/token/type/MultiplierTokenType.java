package linter.token.type;

import linter.visitor.Visitor;

public enum MultiplierTokenType implements TokenType{
    MULTIPLY_OP, DIVIDE_OP, REMINDER_OP, INTEGER_DIV_OP;

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

}