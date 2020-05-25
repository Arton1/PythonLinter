package linter.token.type;

import linter.visitor.TreeElementVisitor;

public enum AssignmentTokenType implements TokenType{
    NORMAL_AS, MULTIPLY_AS, DIVIDE_AS, REMINDER_AS,
    POWER_AS, ADD_AS, SUBSTRACT_AS, INT_DIVIDE_AS;

    @Override
    public void accept(TreeElementVisitor visitor){
        visitor.visit(this);
    }
}