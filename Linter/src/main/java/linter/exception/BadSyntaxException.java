package linter.exception;

public class BadSyntaxException extends LocalizableException{

    private static final long serialVersionUID = 1L;

    public BadSyntaxException(int line, int column) {
        super(line, column);
    }

    public BadSyntaxException(){
    }

}