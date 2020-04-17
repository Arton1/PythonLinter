package token;

import token.type.TokenType;

public class Token {
    private TokenType type;
    private int linePosition;
    private int columnPosition;

    public Token(TokenType type, int linePosition, int columnPosition){
        this.type = type;
        this.linePosition = linePosition;
        this.columnPosition = columnPosition;
    }

    public TokenType getType() {
        return type;
    }

    public int getLinePosition(){
        return linePosition;
    }

    public int getColumnPosition(){
        return columnPosition;
    }
}