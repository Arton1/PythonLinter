package token;

import token.type.TokenType;

public class Token {
    private TokenType type;
    private int line;
    private int column;

    public Token(TokenType type, int line, int column){
        this.type = type;
        this.line = line;
        this.column = column;
    }

    public Token(TokenType type){
        this.type = type;
    }

    public TokenType getType() {
        return type;
    }

    public int getLine(){
        return line;
    }

    public int getColumn(){
        return column;
    }

    public void addPosition(int line, int column){
        this.line = line;
        this.column = column;
    }
}