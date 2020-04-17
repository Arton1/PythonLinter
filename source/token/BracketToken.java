package token;

public class BracketToken {
    public enum BracketType {
        CURLY_BEGIN, CURLY_END, SQUARED_BEGIN, 
        SQUARED_END, ROUNDED_BEGIN, ROUNDED_END
    }

    BracketType type;

    BracketToken(BracketType type){
        this.type = type;
    }
}