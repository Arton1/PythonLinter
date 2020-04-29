package linter.token.type;

import linter.visitor.Visitor;

public enum BlockTokenType implements TokenType{
    NEWLINE, INDENT, TWO_DOTS;

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

}