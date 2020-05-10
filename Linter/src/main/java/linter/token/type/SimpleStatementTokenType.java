package linter.token.type;

import linter.visitor.Visitor;

public enum SimpleStatementTokenType implements TokenType {
    PASS, BREAK, CONTINUE, RETURN, IMPORT, AS, DOT, COMMA, FROM, RETURN_HINT;

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }
}