package linter;

import java.io.FileNotFoundException;
import java.io.IOException;

final class ErrorHandler {

    public static final void handleException(FileNotFoundException e) throws FileNotFoundException {
        System.out.println();
        System.out.print("File not found: " + e.getMessage());
        //cleanup needed
        throw new FileNotFoundException();
    }

    public static final void handleException(IOException e){
        System.out.println();
        System.out.print("IO exception occured: " + e.getMessage());
        //cleanup needed
        System.exit(2);
    }

    public static final void handleBadTokenError(int line, int column){
        System.out.println();
        System.out.print("Error: "+line+";"+column+" : Bad token");
        //cleanup needed
        System.exit(3);
    }
}