package token;

public class LogicToken implements Token{
    public enum LogicType{
        AND, OR, NOT
    }

    LogicType type;

    LogicToken(LogicType type){
        this.type = type;
    }
}