package linter.token.type;

import linter.visitor.Visitor;

public enum CompoundStatementTokenType implements TokenType {
    IF, ELIF, ELSE, 
    WHILE, 
    FOR,
    FUN,
    CLASS;

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

}