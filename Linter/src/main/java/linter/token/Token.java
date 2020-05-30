package linter.token;

import linter.syntax_tree.TreeElement;
import linter.syntax_tree.TreeElementVisitor;
import linter.token.type.TokenType;

public class Token implements TreeElement {
    TokenType tokenType;
    int line;
    int column;

    public Token(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public Token(TokenType tokenType, int line, int column) {
        this.tokenType = tokenType;
        this.line = line;
        this.column = column;
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
    public void accept(TreeElementVisitor visitor) {
        visitor.visit(this);
    }
}