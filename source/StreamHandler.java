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
        currentLinePosition = 1;
        currentColumnPosition = 1;
        nextTokenColumnPosition = -1;
        EOF = false;
        EOL = true; //read next line at next character reading
    }

    public char readCharacter() { 
        if(EOL){ //no line, read next line
            currentColumnPosition = 1;
            EOF = setCursorAtNextCharacter(); //set cursor at next true character
            if(EOF) //no more lines
                return EOF_CHARACTER;
            currentTokenLinePosition = currentLinePosition++;
            currentTokenColumnPosition = currentColumnPosition;
            EOL = false;
        }
        else if(line.length() == 0){ //scanner doesnt contain new line characters in lines
            currentColumnPosition++;
            return EOL_CHARACTER; //simulate EOL in line
        }
        if(nextTokenColumnPosition != -1){ //next token on the same line started
            currentTokenColumnPosition = nextTokenColumnPosition;
            nextTokenColumnPosition = -1;
        }
        char c = line.charAt(0);
        if(c == SPACE_CHARACTER)
            if(passSpaces()) { //check line contains only spaces
                currentColumnPosition++; //adding EOL character to line
                return EOL_CHARACTER; //simulate EOL
            }
            else
                return SPACE_CHARACTER;
        if(c == EOL_CHARACTER){
            EOL = true; //no more characters, load next line at next query
            return c;
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
            line = c + line;
            currentColumnPosition--;
        }
        if(!EOL)
            nextTokenColumnPosition = currentColumnPosition; //save for next token on the same line
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