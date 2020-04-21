package linter.token;

public class BadTokenException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private int line;
    private int column;

    public BadTokenException(int line, int column){
        this.line = line;
        this.column = column;
    } 

    public int getLine(){
        return line;
    }

    public int getColumn(){
        return column;
    }
}