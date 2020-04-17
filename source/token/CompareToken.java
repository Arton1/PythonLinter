package token;


public class CompareToken implements Token {
    public enum CompareType {
        LESS, MORE, LESS_EQUAL, MORE_EQUAL, EQUAL, 
        OTHER_THAN, IN, NOT_IN, IS, IS_NOT
    }

    CompareType type;
    
    public CompareToken(CompareType type){
        this.type = type;
    }
}