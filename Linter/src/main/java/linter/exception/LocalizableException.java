package linter.exception;

import linter.token.Token;

public abstract class LocalizableException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    private int line;
    private int column;

    protected LocalizableException(int line, int column){
        this.line = line;
        this.column = column;
    }

    protected LocalizableException(Token token){
        line = token.getLine();
        column = token.getColumn();
    }

    protected LocalizableException(){
    }

    public int getLine(){
        return line;
    }

    public int getColumn(){
        return column;
    }

    public void setLine(int line){
        this.line = line;
    }

    public void setColumn(int column){
        this.column = column;
    }
}