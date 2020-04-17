import java.io.*;
import java.util.Scanner;

public class StreamHandler {
    private Scanner scanner;

    StreamHandler(){
        scanner = new Scanner(System.in);
        initialize();
    }

    StreamHandler(String filePath){
        try{
            scanner = new Scanner(new File(filePath));
        }
        catch(FileNotFoundException e){
            ErrorHandler.handleException(e);
        }
        initialize();
    }

    private void initialize(){
        scanner.useDelimiter("");
    }

    public char readCharacter() { 
        return scanner.next().charAt(0);
    }
}