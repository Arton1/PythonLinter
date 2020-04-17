package token;

import token.type.TokenType;

public class IdentifierToken extends Token {
    String identifier;

    IdentifierToken(TokenType tokenType, int line, int column){
        super(tokenType, line, column);
    }

    String getIdentifier(){
        return identifier;
    }
}