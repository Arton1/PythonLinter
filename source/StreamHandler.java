import java.io.*;
import java.util.Scanner;

public class StreamHandler {
    private Scanner scanner;
    private String line;
    private int currentLinePosition;
    private int currentColumnPosition;
    private boolean EOF;
    private boolean EOL;
    private int currentTokenLinePosition;
    private int currentTokenColumnPosition;
    private int nextTokenColumnPosition;

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
        nextTokenColumnPosition = -1;
        EOF = false;
        EOL = true;
    }

    public char readCharacter() { 
        char c;
        if(EOL){
            currentColumnPosition = 1;
            EOF = setCursorAtNextCharacter();
            if(EOF)
                return EOFCharacter;
            currentTokenLinePosition = currentLinePosition++;
            currentTokenColumnPosition = currentColumnPosition;
            EOL = false;
        }
        else if(line.length() == 0){
            EOL = true;
            return EOLCharacter;
        }
        if(nextTokenColumnPosition != -1){ //next token on the same line started
            currentTokenColumnPosition = nextTokenColumnPosition;
            nextTokenColumnPosition = -1;
        }
        c = line.charAt(0);
        line = line.substring(1);
        currentColumnPosition++;
        return c;
    }

    private boolean processEmptyLines(){ //returns EOF status
        currentColumnPosition = 1;
        do {
            currentLinePosition++;
            if (scanner.hasNextLine())
                line = scanner.nextLine();
            else
                return true; //is EOF
        } while(line.length() == 0);
        return false; //not EOF
    }

    public void returnCharacter(char c){
        if(c == ' ')
            EOL = passSpaces();
        else
            if (!EOL){
                line = c + line;
                currentColumnPosition--;
        }
        if(!EOL)
            nextTokenColumnPosition = currentColumnPosition; //save for next token on the same line
    }

    private boolean passSpaces(){ //EOL status
        while (line.length() != 0 && line.charAt(0) == ' '){
            line = line.substring(1, line.length());
            currentColumnPosition++;
        }
        if(line.length() == 0)
            return true; //is EOL
        return false; //not EOL
    }

    public int getCurrentTokenLinePosition(){
        return currentTokenLinePosition;
    }

    public int getCurrentTokenColumnPosition(){
        return currentTokenColumnPosition;
    }

    public boolean isEOF(){
        return EOF;
    }

    private boolean setCursorAtNextCharacter(){ //returns EOF status
        if(scanner.hasNextLine())
            line = scanner.nextLine();
        else //EOF status
            return true;
        if(line.length() == 0)
            if(processEmptyLines())
                return true;
        if(line.charAt(0) == ' ')
            passSpaces(); //ignore spaces
        while(line.length() == 0){ //empty line
            if(processEmptyLines()) //check if EOF
                return true;
            if(line.charAt(0) == ' ')
                passSpaces(); //ignore spaces
        }
        return false;
    }
}