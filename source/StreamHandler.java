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
            scanner.close();
            ErrorHandler.handleException(e);
        }
        initialize();
    }

    private void initialize(){
        scanner.useDelimiter("");
        line = scanner.nextLine();
        currentLinePosition = 1;
        currentColumnPosition = 1;
    }

    public char readCharacter() { 
        if(line.length() == 0){
            currentColumnPosition = 0;
            if (!scanner.hasNextLine()){
                return 0;
            }
            line = scanner.nextLine();
            currentLinePosition++;
        }
        char c = line.charAt(0);
        line = line.substring(1, line.length());
        currentColumnPosition++;
        return c;
    }

    public void returnCharacter(char c){
        line = c + line;
        currentColumnPosition--;
    }

    public void ignoreSpaces(){
        char c;
        while((c = readCharacter()) == ' ');
        returnCharacter(c);
    }

    public int getCurrentLinePosition(){
        return currentLinePosition;
    }

    public int getCurrentColumnPosition(){
        return currentColumnPosition;
    }
}