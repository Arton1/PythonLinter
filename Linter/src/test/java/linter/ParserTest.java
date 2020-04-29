package linter;

import org.junit.jupiter.api.Test;

import linter.syntax_tree.SyntaxTree;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    private Parser createParser(String input){
        StreamSimulator stream = new StreamSimulator(input);
        Lexer lexer = new Lexer(stream);
        return new Parser(lexer);
    }

    @Test
    public void getNextSyntaxTree_Empty_ExpectedSingleNode(){
        String input = StreamSimulator.EOL;
        Parser parser = createParser(input);
        SyntaxTree tree = parser.getNextSyntaxTree();
        assertEquals(tree.size(), 1);
    }
}