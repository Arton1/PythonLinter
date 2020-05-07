package linter.exception;

public class IndentationException extends LocalizableException{

    private static final long serialVersionUID = 1L;

    int currentIndentation;
    int targetIndentation;

    IndentationException(int line, int column, int targetIndentation, int currentIndentation){
        super(line, column);
        this.targetIndentation = targetIndentation;
        this.currentIndentation = currentIndentation;
    }

    IndentationException(int targetIndentation, int currentIndentation){
        this.targetIndentation = targetIndentation;
        this.currentIndentation = currentIndentation;
    }
    
}