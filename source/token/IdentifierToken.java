package token;

import token.type.TokenType;

public class IdentifierToken extends Token {
    String identifier;

    public IdentifierToken(TokenType tokenType, String identifier){
        super(tokenType);
        this.identifier = identifier;
    }

    String getIdentifier(){
        return identifier;
    }
}