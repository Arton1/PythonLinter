package token;

import token.type.TokenType;

public class NumberToken extends Token {
    double value;
    
    NumberToken(TokenType tokenType, int line, int column, double value){
        super(tokenType, line, column);
        this.value = value;
    }

    double getValue(){
        return value;
    }
}