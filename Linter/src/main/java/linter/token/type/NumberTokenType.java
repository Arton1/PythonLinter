package linter.token.type;

import linter.visitor.TreeElementVisitor;

public enum NumberTokenType implements TokenType{
    INTEGER, DOUBLE;

    @Override
    public void accept(TreeElementVisitor visitor){
        visitor.visit(this);
    }

}