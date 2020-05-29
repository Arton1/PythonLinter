package linter.token.type;

import linter.syntax_tree.TreeElementVisitor;

public enum SimpleStatementTokenType implements TokenType {
    PASS, BREAK, CONTINUE, RETURN, IMPORT, AS, DOT, COMMA, FROM, RETURN_HINT;

    @Override
    public void accept(TreeElementVisitor visitor){
        visitor.visit(this);
    }
}