package linter.token.type;

import linter.visitor.Visitor;

public enum AssignmentTokenType implements TokenType{
    NORMAL_AS, MULTIPLY_AS, DIVIDE_AS, REMINDER_AS,
    POWER_AS, ADD_AS, SUBSTRACT_AS, INT_DIVIDE_AS;

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }
}