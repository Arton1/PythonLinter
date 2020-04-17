import java.io.FileNotFoundException;
import java.io.IOException;

final class ErrorHandler {
    public static final void handleException(FileNotFoundException e){
        System.out.println("File not found: " + e.getMessage());
        System.exit(1);
    }

    public static final void handleException(IOException e){
        System.out.println("IO exception occured: " + e.getMessage());
    }

    public static final void handleBadTokenError(){
        System.out.println("Bad token");
    }
}