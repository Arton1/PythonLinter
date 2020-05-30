package linter.exception;

import linter.token.Token;

public class SemanticsException extends LocalizableException {

    private static final long serialVersionUID = 1L;
    String message;

    public SemanticsException(String message, int line, int column){
        super(line, column);
        this.message = message;
    }

    public SemanticsException(String message, Token subtreeFirstToken){
        super(subtreeFirstToken);
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}