package linter;

import java.io.ByteArrayInputStream;

public class StreamSimulator {
    static final String EOL = Character.toString(StreamHandler.EOL_CHARACTER);

    public static void simulateInput(String input){
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }
}