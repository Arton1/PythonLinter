package linter.token.type;

import linter.visitor.Visitor;

public enum IdentifierTokenType implements TokenType {
    NAME;

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }
}