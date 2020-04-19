package linter.token;

import linter.token.type.NumberTokenType;

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