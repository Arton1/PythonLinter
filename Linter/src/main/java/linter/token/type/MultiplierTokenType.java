package linter.token.type;

import linter.syntax_tree.TreeElementVisitor;

public enum MultiplierTokenType implements TokenType{
    MULTIPLY_OP, DIVIDE_OP, REMINDER_OP, INTEGER_DIV_OP;

    @Override
    public void accept(TreeElementVisitor visitor){
        visitor.visit(this);
    }

}