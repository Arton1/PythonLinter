package linter.token;

import linter.token.type.TokenType;

public class IdentifierToken extends Token {
    String identifier;

    public IdentifierToken(TokenType tokenType, String identifier){
        super(tokenType);
        this.identifier = identifier;
    }

    public String getIdentifier(){
        return identifier;
    }
}