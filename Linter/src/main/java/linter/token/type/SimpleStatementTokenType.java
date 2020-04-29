package linter.token.type;

import linter.visitor.Visitor;

public enum SimpleStatementTokenType implements TokenType {
    PASS, BREAK, CONTINUE, RETURN, IMPORT, AS, DOT, COMMA;

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }
}