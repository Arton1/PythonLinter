package token;

public class SimpleStatementToken implements Token{
    public enum SimpleStatementType{
        PASS, BREAK, CONTINUE, RETURN, IMPORT, AS, DOT, COMMA
    }

    SimpleStatementType type;

    SimpleStatementToken(SimpleStatementType type){
        this.type = type;
    }
}