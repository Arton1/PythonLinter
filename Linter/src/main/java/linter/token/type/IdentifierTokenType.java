package linter.token.type;

import linter.visitor.TreeElementVisitor;

public enum IdentifierTokenType implements TokenType {
    NAME;

    @Override
    public void accept(TreeElementVisitor visitor){
        visitor.visit(this);
    }
}