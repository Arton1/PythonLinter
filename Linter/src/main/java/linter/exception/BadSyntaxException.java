package linter.exception;

public class BadSyntaxException extends LocalizableException{

    private static final long serialVersionUID = 1L;

    BadSyntaxException(int line, int column) {
        super(line, column);
    }
}