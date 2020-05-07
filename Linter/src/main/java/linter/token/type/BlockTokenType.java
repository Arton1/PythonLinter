package linter.token.type;

import linter.visitor.Visitor;

public enum BlockTokenType implements TokenType{
    NEWLINE, INDENT, TWO_DOTS, EOF;

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

}