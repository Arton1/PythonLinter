package linter;

import org.junit.jupiter.api.Test;

import linter.syntax_tree.SyntaxTree;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    private Parser createParser(String input){
        StreamSimulator.simulateInput(input);
        Lexer lexer = new Lexer(new StreamHandler());
        return new Parser(lexer);
    }

    @Test
    public void getNextSyntaxTree_Empty_ExpectedSingleNode(){
        String input = StreamSimulator.EOL;
        Parser parser = createParser(input);
        SyntaxTree tree = parser.getNextSyntaxTree();
        assertNull(tree); //EOF, so no tree
    }

    @Test
    public void getNextSyntaxTree_Pass_ExpectedTwoNodes(){
        String input = "pass";
        Parser parser = createParser(input);
        SyntaxTree tree = parser.getNextSyntaxTree();
        assertEquals(tree.size(), 4); //All EOLs get ignored at the beginning, so tree is empty
    }
}