package linter.token.type;

import linter.visitor.TreeElementVisitor;

public enum LogicTokenType implements TokenType {
    AND, OR, NOT, TRUE, FALSE;

    @Override
    public void accept(TreeElementVisitor visitor){
        visitor.visit(this);
    }

}