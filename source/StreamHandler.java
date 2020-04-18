import java.io.*;
import java.util.Scanner;

public class StreamHandler {
    private Scanner scanner;
    private String line;
    private int currentLinePosition;
    private int currentColumnPosition;
    private boolean EOF;
    private boolean loadNextLine;

    static final char EOFCharacter = (char)-1;
    static final char EOLCharacter = (char)10;

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
        currentLinePosition = 1;
        currentColumnPosition = 1;
        EOF = false;
        loadNextLine = true;
    }

    public char readCharacter() { 
        char c;
        if(loadNextLine){
            setCursorAtNextCharacter();
            if(isEOF())
                return EOFCharacter;
            loadNextLine = false;
        }
        else if(line.length() == 0){
            loadNextLine = true;
            return EOLCharacter;
        }
        c = line.charAt(0);
        line = line.substring(1);
        currentColumnPosition++;
        return c;
    }

    private char processEmptyLines(){
        currentColumnPosition = 1;
        do {
            currentLinePosition++;
            if (scanner.hasNextLine())
                line = scanner.nextLine();
            else {
                EOF = true;
                return EOFCharacter;
            }
        } while(line.length() == 0);
        return EOLCharacter;
    }

    public void returnCharacter(char c){
        if (c != EOLCharacter && c != ' '){ 
            line = c + line;
            currentColumnPosition--;
        }
    }

    private void passSpaces(){
        do {
            line = line.substring(1, line.length());
            currentColumnPosition++;
        } 
        while (line.length() != 0 && line.charAt(0) == ' ');
    }

    public int getCurrentLinePosition(){
        return currentLinePosition;
    }

    public int getCurrentColumnPosition(){
        return currentColumnPosition;
    }

    public boolean isEOF(){
        return EOF;
    }

    private char setCursorAtNextCharacter(){
        char specialCharacter = 0;
        if(line.length() == 0){
            specialCharacter = processEmptyLines(); //ignore empty lines
            if(specialCharacter == EOFCharacter)
                return specialCharacter;
        }
        if(line.charAt(0) == ' '){ 
            passSpaces(); //ignore spaces
            specialCharacter = ' ';
        }
        while(line.length() == 0){ //empty line
            specialCharacter = processEmptyLines(); //ignore empty lines
            if(specialCharacter == EOFCharacter) //check if EOF
                return specialCharacter;
            if(line.charAt(0) == ' ')
                passSpaces(); //ignore spaces
        }
        return specialCharacter;
    }
}