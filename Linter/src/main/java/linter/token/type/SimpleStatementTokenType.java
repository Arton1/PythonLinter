package linter.token.type;

import linter.visitor.TreeElementVisitor;

public enum SimpleStatementTokenType implements TokenType {
    PASS, BREAK, CONTINUE, RETURN, IMPORT, AS, DOT, COMMA, FROM, RETURN_HINT;

    @Override
    public void accept(TreeElementVisitor visitor){
        visitor.visit(this);
    }
}