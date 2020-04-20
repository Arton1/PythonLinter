package linter;

import linter.StreamHandler;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
public class StreamHandlerTest
{

    static final String EOL = Character.toString(StreamHandler.EOL_CHARACTER);

    private InputStream backupInputStream;

    private void simulateConsole(String string){
        backupInputStream = System.in; //save for revert
        System.setIn(new ByteArrayInputStream(string.getBytes())); //simulate standard input
    }

    private void revertConsole(){
        System.setIn(backupInputStream);
    }

    @Test
    public void Constructor_NoFile_ExceptionThrown()
    {
        String notExistingFile = "?.txt";
        assertThrows(FileNotFoundException.class, () -> {new StreamHandler(notExistingFile);});
    }

    @Test
    public void ReadCharacter_EmptyStream_EOF(){
        simulateConsole("");
        StreamHandler stream = new StreamHandler();
        assertEquals((char)-1, stream.readCharacter(), "Not EOF");
        revertConsole();
    }

    @Test
    public void ReadCharacter_LineContainingCharacters_ExpectedCharacters(){
        String line = "aA']0@";
        simulateConsole(line);
        StreamHandler stream = new StreamHandler();
        int characterPosition = 0;
        while(characterPosition<line.length() && line.charAt(characterPosition) == stream.readCharacter())
            characterPosition++;
        if(stream.readCharacter() == (char)-1) //EOL
            characterPosition++;
        assertEquals(line.length(), characterPosition, "Character not same at position: " + characterPosition + "("+(line+(char)-1).charAt(characterPosition)+")");
        revertConsole();
    }

    /**
     * Should ignore all the spaces to set reader at next character to return.
     * Should return EOL, because no character should be found.
     */
    @Test
    public void ReadCharacter_MultipleSpacesInSingleLine_ExpectedEOF(){
        String line = "     ";
        simulateConsole(line);
        StreamHandler stream = new StreamHandler();
        assertEquals(StreamHandler.EOF_CHARACTER, stream.readCharacter());
        revertConsole();
    }

    /**
     * Should ignore all lines to set reader at next character to return.
     * Should return EOL, because no character should be found.
     */
    @Test
    public void ReadCharacter_MultipleLines_ExpectedEOF(){
        String lines = EOL+EOL+EOL;
        simulateConsole(lines);
        StreamHandler stream = new StreamHandler();
        char c = stream.readCharacter();
        assertEquals(StreamHandler.EOF_CHARACTER, c);
        revertConsole();
    }

    @Test
    public void ReadCharacter_LineAndMultipleSpaces_ExpectedEOF(){
        String lines = EOL+"    ";
        simulateConsole(lines);
        StreamHandler stream = new StreamHandler();
        char c = stream.readCharacter();
        assertEquals(StreamHandler.EOF_CHARACTER, c);
        revertConsole();
    }

    @Test
    public void ReadCharacter_SpacesAndMultipleLines_ExpectedEOF(){
        String lines = "  "+EOL+EOL;
        simulateConsole(lines);
        StreamHandler stream = new StreamHandler();
        char c = stream.readCharacter();
        assertEquals(StreamHandler.EOF_CHARACTER, c);
        revertConsole();
    }
}
