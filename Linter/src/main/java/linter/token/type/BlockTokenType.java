package linter.token.type;

import linter.visitor.TreeElementVisitor;

public enum BlockTokenType implements TokenType{
    NEWLINE, INDENT, TWO_DOTS, EOF;

    @Override
    public void accept(TreeElementVisitor visitor){
        visitor.visit(this);
    }

}