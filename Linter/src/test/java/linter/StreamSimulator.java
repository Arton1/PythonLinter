package linter;

import java.io.ByteArrayInputStream;

public class StreamSimulator extends StreamHandler {
    static final String EOL = Character.toString(StreamHandler.EOL_CHARACTER);

    StreamSimulator(String input) {
        super();
        System.setIn(new ByteArrayInputStream(input.getBytes())); // simulate standard input
    }
}