package linter.exception;

public class BadTokenException extends LocalizableException{

    private static final long serialVersionUID = 1L;

    public BadTokenException(int line, int column) {
        super(line, column);
    } 

}