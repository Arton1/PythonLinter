package token;

import token.type.NumberTokenType;

public class IntegerNumberToken extends Token {
    int value;

    public IntegerNumberToken(int value) {
        super(NumberTokenType.INTEGER);
        this.value = value;
    }

    public int getValue(){
        return value;
    }

}