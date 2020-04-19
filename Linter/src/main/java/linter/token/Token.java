package linter.token;

import linter.token.type.TokenType;

public class Token {
    TokenType tokenType;
    int line;
    int column;

    public Token(TokenType tokenType){
        this.tokenType = tokenType;
    }

    public int getLine(){
        return line;
    }

    public int getColumn(){
        return column;
    }

    public void setPosition(int line, int column){
        this.line = line;
        this.column = column;
    }

    public TokenType getTokenType(){
        return tokenType;
    }
}