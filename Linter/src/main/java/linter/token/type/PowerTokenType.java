package linter.token.type;

import linter.visitor.TreeElementVisitor;

public enum PowerTokenType implements TokenType {
    POWER;

    @Override
    public void accept(TreeElementVisitor visitor){
        visitor.visit(this);
    }

}