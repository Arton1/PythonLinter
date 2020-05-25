package linter.token.type;

import linter.visitor.TreeElementVisitor;

public enum SignTokenType implements TokenType {
    PLUS, MINUS, NEGATION;

    @Override
    public void accept(TreeElementVisitor visitor){
        visitor.visit(this);
    }

}