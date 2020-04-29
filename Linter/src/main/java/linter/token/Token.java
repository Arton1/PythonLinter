package linter.token;

import linter.syntax_tree.TreeElement;
import linter.token.type.TokenType;
import linter.visitor.Visitor;

public class Token implements TreeElement {
    TokenType tokenType;
    int line;
    int column;

    public Token(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public void setPosition(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}