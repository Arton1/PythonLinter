package linter;

import java.io.FileNotFoundException;
import java.io.IOException;

import linter.token.BadTokenException;


final class ErrorHandler {

    public static final void handleFileNotFoundException(FileNotFoundException e){
        System.out.println();
        System.out.print("Error : File not found : "+ e.getMessage());
        System.exit(1);
    }

    public static final void handleIOException(IOException e){
        System.out.println();
        System.out.print("Error : Failed IO operation : " + e.getMessage());
        //cleanup needed
        System.exit(2);
    }

    public static final void handleBadTokenException(BadTokenException e){
        System.out.println();
        System.out.print("Error : Bad token : "+e.getLine()+";"+e.getColumn());
        //cleanup needed
        System.exit(3);
    }
}