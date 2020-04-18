package token;

import token.type.TokenType;

public class NumberToken extends Token {
    double value;
    
    NumberToken(TokenType tokenType, double value){
        super(tokenType);
        this.value = value;
    }

    double getValue(){
        return value;
    }
}