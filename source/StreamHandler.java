import java.io.*;
import java.util.Scanner;

public class StreamHandler {
    private Scanner scanner;
    private String line;
    private int currentLinePosition;
    private int currentColumnPosition;
    private boolean EOF;
    private boolean EOL;

    static final char EOF_CHARACTER = (char)-1;
    static final char EOL_CHARACTER = (char)10;
    static final char SPACE_CHARACTER = ' ';

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
        currentLinePosition = 0;
        currentColumnPosition = 0;
        EOF = false;
        line = null;
    }

    public char readCharacter() { 
        if(line == null){ //no line, read next line
            currentColumnPosition = 0;
            EOF = setCursorAtNextCharacter(); //set cursor at next true character
            if(EOF) //no more lines
                return EOF_CHARACTER;
            currentLinePosition++;
        }
        if(line.length() == 0){ //scanner doesnt contain any new line characters in lines
            line = null;
            return EOL_CHARACTER; //simulate EOL in line
        }
        char c = line.charAt(0);
        if(c == SPACE_CHARACTER)
            if(passSpaces()) { //check line contains only spaces and pass any mass spaces
                currentColumnPosition++; //adding EOL character to line
                return EOL_CHARACTER; //simulate EOL
            }
            else
                return SPACE_CHARACTER;
        if(c == EOL_CHARACTER){
            line = null;
            currentColumnPosition++;
            return EOL_CHARACTER;
        }
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
        if(c != SPACE_CHARACTER){
            if(line != null){
                line = c + line;
                currentColumnPosition--;
            }
            else
                line = Character.toString(c);
        }
    }

    private boolean passSpaces(){ //EOL status
        while (line.length() != 0 && line.charAt(0) == SPACE_CHARACTER){ //pass all spaces
            line = line.substring(1, line.length());
            currentColumnPosition++;
        }
        if(line.length() == 0)
            return true; //is EOL, no EOL character
        return false; //not empty, probably EOL character in there
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

    private boolean setCursorAtNextCharacter(){ //returns EOF status
        if(scanner.hasNextLine())
            line = scanner.nextLine(); //load line
        else //EOF status
            return true;
        if(line.length() == 0) //empty line
            if(processEmptyLines()) //pass empty lines
                return true;
        if(line.charAt(0) == SPACE_CHARACTER) //first character is space after getting next line
            passSpaces(); //ignore spaces in this line
        while(line.length() == 0){ //new line had only spaces. Pass all lines with the same cases.
            if(processEmptyLines()) //check if EOF. Pass all empty spaces
                return true;
            if(line.charAt(0) == SPACE_CHARACTER) //pass spaces
                passSpaces(); //ignore spaces
        }
        return false;
    }
}