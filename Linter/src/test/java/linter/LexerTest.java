package linter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import linter.token.Token;
import linter.token.type.AssignmentTokenType;
import linter.token.type.BlockTokenType;
import linter.token.type.IdentifierTokenType;
import linter.token.type.NumberTokenType;
import linter.exception.BadTokenException;

public class LexerTest {
    public static final String EOL = StreamSimulator.EOL;

    private Lexer createLexer(String input){
        StreamSimulator stream = new StreamSimulator(input);
        Lexer lexer = new Lexer(stream);
        return lexer;
    }

    @Test
    public void getToken_Empty_ExpectedNull(){
        String input = "";
        Lexer lexer = createLexer(input);
        assertEquals(null, lexer.getToken());
    }
    
    @Test
    public void getToken_Character_ExpectedToken(){
        String input = "abcd";
        Lexer lexer = createLexer(input);;
        assertEquals(IdentifierTokenType.NAME, lexer.getToken().getTokenType());
    }

    @Test
    public void getToken_Zero_ExpectedNumberToken(){
        String input = "0";
        Lexer lexer = createLexer(input);
        assertEquals(NumberTokenType.INTEGER, lexer.getToken().getTokenType());
    }

    @Test
    public void getToken_ZerosAndOne_ExpectedNumberToken(){
        String input = "0";
        Lexer lexer = createLexer(input);
        assertEquals(NumberTokenType.INTEGER, lexer.getToken().getTokenType());
    }

    @Test
    public void getToken_Indent_ExpectedIndentToken(){
        String input = "	";
        Lexer lexer = createLexer(input);
        assertEquals(BlockTokenType.INDENT, lexer.getToken().getTokenType());
    }

    /**
     * Three tokens, because last token is EOL
     */
    @Test
    public void getToken_IdentifiersDelimitedBySpaces_Expected3Tokens(){
        String input = "iden1 iden2";
        Lexer lexer = createLexer(input);
        int numberOfTokens = 0;
        while(lexer.getToken() != null)
            numberOfTokens++;
        assertEquals(3, numberOfTokens);
    }

    @Test
    public void getToken_MinusEqualsMinus_ExpectedSubstractAssignmentToken(){
        String input = "-=-";
        Lexer lexer = createLexer(input);
        assertEquals(AssignmentTokenType.SUBSTRACT_AS, lexer.getToken().getTokenType());
    }

    @Test
    public void getToken_IdentifierMinusEqualsMinusNumber_Expected5Tokens(){
        String input = "identifier-=-4.3";
        Lexer lexer = createLexer(input);
        int numberOfTokens = 0;
        while(lexer.getToken() != null)
            numberOfTokens++;
        assertEquals(5, numberOfTokens);
    }
    /**
     * Four tokens, because last token is EOL token
     */
    @Test
    public void getToken_IdentifierCommaIdentifier_Expected4Tokens(){
        String input = "iden1,iden2";
        Lexer lexer = createLexer(input);
        int numberOfTokens = 0;
        while(lexer.getToken() != null)
            numberOfTokens++;
        assertEquals(4, numberOfTokens);
    }

    @Test
    public void getToken_IdentifierEOLIndentNumber_Expected5Tokens(){
        String input = "identifier"+EOL+"	4.32"; //4 tokens + EOL
        Lexer lexer = createLexer(input);
        int numberOfTokens = 0;
        while(lexer.getToken() != null)
            numberOfTokens++;
        assertEquals(5, numberOfTokens);
    }

    @Test
    public void getToken_ZeroZeroOne_ThrowsBadTokenException(){
        String input = "001";
        Lexer lexer = createLexer(input);
        assertThrows(BadTokenException.class, () -> lexer.getToken());
    }

    @Test
    public void getToken_Identifier_ExpectedPosition(){
        String input = "__ident123";
        Lexer lexer = createLexer(input);
        Token token = lexer.getToken();
        assertTrue(token.getLine() == 1 && token.getColumn() == 1, "Expected position: 1;1 Position: " + token.getLine() + " " + token.getColumn());
    }

    @Test
    public void getToken_IdentifierAfterSpaces_ExpectedPosition(){
        String input = "   __ident123";
        Lexer lexer = createLexer(input);
        Token token = lexer.getToken();
        assertTrue(token.getLine() == 1 && token.getColumn() == 4, "Expected position: 1;4 Position: " + token.getLine() + ";" + token.getColumn());
    }

    @Test
    public void getToken_IdentifierAfterEOLs_ExpectedPosition(){
        String input = EOL+EOL+"__ident123";
        Lexer lexer = createLexer(input);
        Token token = lexer.getToken();
        assertTrue(token.getLine() == 3 && token.getColumn() == 1, "Expected position: 3;1 Position: " + token.getLine() + ";" + token.getColumn());
    }

    @Test
    public void getToken_IdentifierAfterEOLsAndSpaces_ExpectedPosition(){
        String input = EOL+EOL+"    __ident123";
        Lexer lexer = createLexer(input);
        Token token = lexer.getToken();
        assertTrue(token.getLine() == 3 && token.getColumn() == 5, "Expected position: 3;4 Position: " + token.getLine() + ";" + token.getColumn());
    }
}