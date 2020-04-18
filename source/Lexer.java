import token.DoubleNumberToken;
import token.IdentifierToken;
import token.IntegerNumberToken;
import token.Token;
import token.type.*;
import java.util.HashMap;

public class Lexer {
    private StreamHandler stream;
    private HashMap<String, TokenType> tokenTable;

    static final String smallLetters = "abcdefghijklmnopqrstuvwxyz";
    static final String largeLetters = "ABCDEFGHIJKLMNOPQRSTUVW";
    static final String numbers = "0123456789";
    static final String numbersWithoutZero = "123456789";
    static final String acceptableSpecialCharacters = "+-=*/<>!.,[](){}\"\'";

    Lexer(StreamHandler stream){
        this.stream = stream;
        createTokenTable();
    }

    void createTokenTable(){
        tokenTable = new HashMap<String, TokenType>();
        tokenTable.put("return", SimpleStatementTokenType.RETURN);
        tokenTable.put("in", CompareTokenType.IN);
        tokenTable.put("pass", SimpleStatementTokenType.PASS);
        tokenTable.put("break", SimpleStatementTokenType.BREAK);
        tokenTable.put("continue", SimpleStatementTokenType.CONTINUE);
        tokenTable.put("is", CompareTokenType.IS);
        tokenTable.put("import", SimpleStatementTokenType.IMPORT);
        tokenTable.put("else", CompoundStatementTokenType.ELSE);
        tokenTable.put("elif", CompoundStatementTokenType.ELIF);
        tokenTable.put("and", LogicTokenType.AND);
        tokenTable.put("as", SimpleStatementTokenType.AS);
        tokenTable.put("or", LogicTokenType.OR);
        tokenTable.put("def", CompoundStatementTokenType.FUN);
        tokenTable.put("class", CompoundStatementTokenType.CLASS);
        tokenTable.put("for", CompoundStatementTokenType.FOR);
        tokenTable.put("not", LogicTokenType.NOT);
        tokenTable.put("+", SimpleStatementTokenType.RETURN);
        tokenTable.put("+=", AssignmentTokenType.ADD_AS);
        tokenTable.put("-=", AssignmentTokenType.SUBSTRACT_AS);
        tokenTable.put("*", MultiplierTokenType.MULTIPLY_OP);
        tokenTable.put("*=", AssignmentTokenType.MULTIPLY_AS);
        tokenTable.put("**", SimpleStatementTokenType.RETURN);
        tokenTable.put("**=", AssignmentTokenType.POWER_AS);
        tokenTable.put("/", MultiplierTokenType.DIVIDE_OP);
        tokenTable.put("/=", AssignmentTokenType.DIVIDE_AS);
        tokenTable.put("//", SimpleStatementTokenType.RETURN);
        tokenTable.put("//=", AssignmentTokenType.REMINDER_AS);
        tokenTable.put("=", AssignmentTokenType.NORMAL_AS);
        tokenTable.put("%=", AssignmentTokenType.REMINDER_AS);
        tokenTable.put("%", MultiplierTokenType.REMINDER_OP);
        tokenTable.put("//", MultiplierTokenType.INTEGER_DIV_OP);
        tokenTable.put("==", CompareTokenType.EQUAL);
        tokenTable.put("<", CompareTokenType.LESS);
        tokenTable.put("<=", CompareTokenType.LESS_EQUAL);
        tokenTable.put("<>", CompareTokenType.OTHER_THAN);
        tokenTable.put(">", CompareTokenType.MORE);
        tokenTable.put(">=", CompareTokenType.MORE_EQUAL);
        tokenTable.put(".", SimpleStatementTokenType.DOT);
        tokenTable.put(",", SimpleStatementTokenType.COMMA);
        tokenTable.put("{", BracketTokenType.CURLY_BEGIN);
        tokenTable.put("}", BracketTokenType.CURLY_END);
        tokenTable.put("[", BracketTokenType.SQUARED_BEGIN);
        tokenTable.put("]", BracketTokenType.SQUARED_END);
        tokenTable.put("(", BracketTokenType.ROUNDED_BEGIN);
        tokenTable.put(")", BracketTokenType.ROUNDED_END);
        tokenTable.put("\'", StringTokenType.DOUBLE_QUOTE);
        tokenTable.put("\"", StringTokenType.SINGLE_QUOTE);
        //need to add more 
    }

    public Token getToken(){
        Token token;
        int tokenLinePosition = stream.getCurrentLinePosition();
        int tokenColumnPosition = stream.getCurrentColumnPosition();
        token = createToken();
        if(token == null) //token doesnt exist, bad token
            ErrorHandler.handleBadTokenError(tokenLinePosition, tokenColumnPosition);
        token.setPosition(tokenLinePosition, tokenColumnPosition);
        return token;
    }

    private Token createToken(){
        char c;
        c = stream.readCharacter();
        String text = "";
        text += c;
        if (smallLetters.indexOf(c) != -1)
            return beginsWithSmallLetterToken(text);
        if (largeLetters.indexOf(c) != -1)
            return beginsWithLargeLetterToken(text);
        if (numbersWithoutZero.indexOf(c) != -1)
            return beginsWithNumberToken(text);
        if (c == '0')
            return beginsWithZeroToken(text);
        return beginsWithSpecialCharacterToken(text);
    }

    private Token beginsWithSmallLetterToken(String text){
        boolean possibleKeyword = true;
        char c;
        while(true){
            c = stream.readCharacter();
            if(smallLetters.indexOf(c) == -1){
                if ((largeLetters+numbers).indexOf(c) == -1)
                    break;
                possibleKeyword = false;
            }
            text += c;
        }
        TokenType tokenType = null;
        if (possibleKeyword)
            tokenType = tokenTable.getOrDefault(text, null);
        stream.returnCharacter(c);
        if(tokenType == null)
            return new IdentifierToken(IdentifierTokenType.NAME, text);
        else
            return new Token(tokenType);
    }

    private Token beginsWithLargeLetterToken(String text){
        char c;
        while((smallLetters+largeLetters+numbers).indexOf(c = stream.readCharacter()) != -1)
            text += c;
        stream.returnCharacter(c);
        return new IdentifierToken(IdentifierTokenType.NAME, text);
    }

    private Token beginsWithNumberToken(String text){
        char c;
        while(numbers.indexOf(c = stream.readCharacter()) != -1) //integer
            text += c;
        if(c == '.'){ //if double
            text += c;
            return processDouble(text);
        }
        else {
            stream.returnCharacter(c);
            return new IntegerNumberToken(Integer.parseInt(text));
        }
    }

    private Token beginsWithZeroToken(String text){
        char c = stream.readCharacter();
        if(c == '.'){
            text += c;
            return processDouble(text);
        }
        if(numbers.indexOf(c) != -1) //c is a number
            return null; //Bad token
        stream.returnCharacter(c);
        return new IntegerNumberToken(0);
    }

    private Token processDouble(String text){
        char c;
        while(numbers.indexOf(c = stream.readCharacter()) != -1)
            text += c;
        stream.returnCharacter(c);
        return new DoubleNumberToken(Double.parseDouble(text));
    }

    private Token beginsWithSpecialCharacterToken(String text){
        char c;
        while(acceptableSpecialCharacters.indexOf(c = stream.readCharacter()) != -1)
            text += c;
        stream.returnCharacter(c);
        TokenType tokenType = tokenTable.getOrDefault(text, null);
        if(tokenType != null)
            return new Token(tokenType);
        else
            return null;
    }

    public boolean isEOF(){
        return stream.isEOF();
    }
}