package token;

public class CompoundStatementToken implements Token {
    public enum CompoundStatementType {
        IF, ELIF, ELSE, 
        WHILE, 
        FOR,
        FUN,
        CLASS
    } 

    CompoundStatementType type;

    CompoundStatementToken(CompoundStatementType type){
        this.type = type;
    }
}