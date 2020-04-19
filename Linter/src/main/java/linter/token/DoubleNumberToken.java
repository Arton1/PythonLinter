package linter.token; 

import linter.token.type.NumberTokenType;

public class DoubleNumberToken extends Token {
    double value;
    
    public DoubleNumberToken(double value){
        super(NumberTokenType.DOUBLE);
        this.value = value;
    }

    public double getValue(){
        return value;
    }
}