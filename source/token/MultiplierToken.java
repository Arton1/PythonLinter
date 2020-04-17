package token;

public class MultiplierToken implements Token{
    public enum MultiplierType{
        MULTIPLY_OP, DIVIDE_OP, REMINDER_OP, INTEGER_DIV_OP
    }

    MultiplierType type;

    MultiplierToken(MultiplierType type){
        this.type = type;
    }
}