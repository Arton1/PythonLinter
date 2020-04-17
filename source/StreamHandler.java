import java.io.*;
import java.util.Scanner;

public class StreamHandler {
    private Scanner scanner;
    private String line;
    private int currentLinePosition;
    private int currentColumnPosition;

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
        if(line.length() == 0){
            line = scanner.nextLine();
            currentColumnPosition++;
        }
        char c = line.charAt(0);
        line = line.substring(1, line.length());
        return c;
    }

    public int getCurrentLinePosition(){
        return currentLinePosition;
    }

    public int getCurrentColumnPosition(){
        return currentColumnPosition;
    }
}