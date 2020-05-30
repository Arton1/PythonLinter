package linter;

import java.io.FileNotFoundException;
import java.io.IOException;

import linter.exception.*;


public final class ErrorHandler {

    public static final void handleFileNotFoundException(FileNotFoundException e){
        System.out.println("Error : StreamHandler : File not found : "+ e.getMessage());
        System.exit(1);
    }

    public static final void handleIOException(IOException e){
        System.out.println("Error : StreamHandler : Failed IO operation : " + e.getMessage());
        System.exit(2);
    }

    public static final void handleBadTokenException(BadTokenException e){
        System.out.println("Error : Lexer : Bad token : "+e.getLine()+";"+e.getColumn());
        System.exit(3);
    }

    public static final void handleBadSyntaxException(BadSyntaxException e){
        System.out.println("Error : Parser : Bad syntax : "+e.getLine()+";"+e.getColumn());
        System.exit(4);
    }

	public static void handleIndentationException(IndentationException e) {
        System.out.println("Error : Parser : Bad indentation : "+e.getLine()+";"+e.getColumn());
        System.exit(5);
    }
    
    public static void handleSemanticsException(SemanticsException e){
        System.out.println("Error : SemanticsAnalizer : " + e.getMessage() + " : "+e.getLine()+";"+e.getColumn());
        System.exit(6);
    }
}