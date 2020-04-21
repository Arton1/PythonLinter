package linter;

import java.io.*;
import java.util.Scanner;

public class StreamHandler {
    private Scanner scanner;
    private String line;
    private int currentLinePosition;
    private boolean EOF;
    private int initialLineSize;

    static final char EOF_CHARACTER = (char)-1;
    static final char EOL_CHARACTER = (char)10;
    static final char SPACE_CHARACTER = ' ';

    StreamHandler(){
        scanner = new Scanner(System.in);
        initialize();
    }

    StreamHandler(String filePath) throws FileNotFoundException{
        File file = new File(filePath);
        if(file.isFile())
            scanner = new Scanner(file);
        else
            throw new FileNotFoundException("File doesn't exist");
        initialize();
    }

    private void initialize(){
        scanner.useDelimiter("");
        currentLinePosition = 0;
        EOF = false;
        line = null;
    }

    public char readCharacter() { 
        if(line == null){ //no line, read next line
            EOF = setNextLine(); //set cursor at next true character
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
            if(passSpaces()) { //check line contains only spaces and pass any multiple spaces
                return EOL_CHARACTER; //simulate EOL
            }
            else
                return SPACE_CHARACTER;
        if(c == EOL_CHARACTER){
            line = null;
            return EOL_CHARACTER;
        }
        line = line.substring(1);
        return c;
    }

    private boolean processEmptyLines(){ //returns EOF status
        do {
            currentLinePosition++;
            if (scanner.hasNextLine())
                line = scanner.nextLine();
            else
                return true; //is EOF
        } while(line.length() == 0);
        initialLineSize = line.length();
        return false; //not EOF
    }

    public void returnCharacter(char c){
        if(c != SPACE_CHARACTER){
            if(line != null){
                line = c + line;
            }
            else
                line = Character.toString(c);
        }
    }

    private boolean passSpaces(){ //EOL status
        while (line.length() != 0 && line.charAt(0) == SPACE_CHARACTER){ //pass all spaces
            line = line.substring(1, line.length());
        }
        if(line.length() == 0)
            return true; //is EOL, no EOL character in line
        return false; //not empty, probably EOL character in there
    }

    private boolean setCursorAtNextCharacter(){
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

    public void prepareForNextToken(){
        if(passSpaces()) //EOL? 
            line = Character.toString(EOL_CHARACTER);
    }


    public int getCurrentLinePosition(){
        return currentLinePosition;
    }

    public int getCurrentColumnPosition(){
        if(line != null)
            return initialLineSize - line.length();
        else
            return initialLineSize+1; //EOL position
    }

    public boolean isEOF(){
        return EOF;
    }

    private boolean setNextLine(){ //returns EOF status
        if(scanner.hasNextLine())
            line = scanner.nextLine(); //load line
        else //EOF status
            return true;
        initialLineSize = line.length();
        return setCursorAtNextCharacter(); //EOF if cant find
    }
}