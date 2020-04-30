package linter;

import linter.token.*;
import linter.token.type.*;
import linter.exception.BadTokenException;
import java.util.HashMap;

public class Lexer {
    private StreamHandler stream;
    private HashMap<String, TokenType> wordsTokenTable; //tokens that only contain letters
    private HashMap<String, TokenType> singletonTokenTable; //special character tokens that have only one letters and can be instantly identified
    private HashMap<String, TokenType> ambiguousTokenTable; //special character tokens that are ambiguous, so need additional characters checking

    private int currentTokenLinePosition;
    private int currentTokenColumnPosition;

    private Token buffer;

    static final String SMALL_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    static final String LARGE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVW_";
    static final String NUMBERS = "0123456789";
    static final String NUMBERS_WITHOUT_ZERO = "123456789";
    static final String SINGLETON_CHARACTERS = ":.,[](){}\"\'\t";
    static final String CAN_HAVE_EQUALS_CHARACTERS = "+-%=!*/<>";

    Lexer(StreamHandler stream){
        this.stream = stream;
        buffer = null;
        createWordsTokenTable();
        createSingletonTokenTable();
        createAmbiguousTokenTable();
    }

    void createWordsTokenTable(){
        wordsTokenTable = new HashMap<String, TokenType>();
        wordsTokenTable.put("return", SimpleStatementTokenType.RETURN);
        wordsTokenTable.put("in", CompareTokenType.IN);
        wordsTokenTable.put("pass", SimpleStatementTokenType.PASS);
        wordsTokenTable.put("break", SimpleStatementTokenType.BREAK);
        wordsTokenTable.put("continue", SimpleStatementTokenType.CONTINUE);
        wordsTokenTable.put("is", CompareTokenType.IS);
        wordsTokenTable.put("import", SimpleStatementTokenType.IMPORT);
        wordsTokenTable.put("else", CompoundStatementTokenType.ELSE);
        wordsTokenTable.put("elif", CompoundStatementTokenType.ELIF);
        wordsTokenTable.put("and", LogicTokenType.AND);
        wordsTokenTable.put("as", SimpleStatementTokenType.AS);
        wordsTokenTable.put("or", LogicTokenType.OR);
        wordsTokenTable.put("def", CompoundStatementTokenType.FUN);
        wordsTokenTable.put("class", CompoundStatementTokenType.CLASS);
        wordsTokenTable.put("for", CompoundStatementTokenType.FOR);
        wordsTokenTable.put("not", LogicTokenType.NOT);
    }

    private void createSingletonTokenTable(){
        singletonTokenTable = new HashMap<String, TokenType>();
        singletonTokenTable.put(".", SimpleStatementTokenType.DOT);
        singletonTokenTable.put(",", SimpleStatementTokenType.COMMA);
        singletonTokenTable.put("{", BracketTokenType.CURLY_BEGIN);
        singletonTokenTable.put("}", BracketTokenType.CURLY_END);
        singletonTokenTable.put("[", BracketTokenType.SQUARED_BEGIN);
        singletonTokenTable.put("]", BracketTokenType.SQUARED_END);
        singletonTokenTable.put("(", BracketTokenType.ROUNDED_BEGIN);
        singletonTokenTable.put(")", BracketTokenType.ROUNDED_END);
        singletonTokenTable.put("\'", StringTokenType.SINGLE_QUOTE);
        singletonTokenTable.put("\"", StringTokenType.DOUBLE_QUOTE);
        singletonTokenTable.put("\t", BlockTokenType.INDENT);
        singletonTokenTable.put(":", BlockTokenType.TWO_DOTS);
        singletonTokenTable.put("\n", BlockTokenType.NEWLINE);
    }

    private void createAmbiguousTokenTable(){
        ambiguousTokenTable = new HashMap<String, TokenType>();
        ambiguousTokenTable.put("/", MultiplierTokenType.DIVIDE_OP);
        ambiguousTokenTable.put("/=", AssignmentTokenType.DIVIDE_AS);
        ambiguousTokenTable.put("+", SignTokenType.PLUS);
        ambiguousTokenTable.put("+=", AssignmentTokenType.ADD_AS);
        ambiguousTokenTable.put("-", SignTokenType.MINUS);
        ambiguousTokenTable.put("-=", AssignmentTokenType.SUBSTRACT_AS);
        ambiguousTokenTable.put("*", MultiplierTokenType.MULTIPLY_OP);
        ambiguousTokenTable.put("*=", AssignmentTokenType.MULTIPLY_AS);
        ambiguousTokenTable.put("%", MultiplierTokenType.REMINDER_OP);
        ambiguousTokenTable.put("%=", AssignmentTokenType.REMINDER_AS);
        ambiguousTokenTable.put("==", CompareTokenType.EQUAL);
        ambiguousTokenTable.put("=", AssignmentTokenType.NORMAL_AS);
        ambiguousTokenTable.put("**", PowerTokenType.POWER);
        ambiguousTokenTable.put("**=", AssignmentTokenType.POWER_AS);
        ambiguousTokenTable.put("//=", AssignmentTokenType.REMINDER_AS);
        ambiguousTokenTable.put("//", MultiplierTokenType.INTEGER_DIV_OP);
        ambiguousTokenTable.put("<", CompareTokenType.LESS);
        ambiguousTokenTable.put("<=", CompareTokenType.LESS_EQUAL);
        ambiguousTokenTable.put(">", CompareTokenType.MORE);
        ambiguousTokenTable.put(">=", CompareTokenType.MORE_EQUAL);
        ambiguousTokenTable.put("!=", CompareTokenType.OTHER_THAN);
    }

    
    // Returns null if EOF
    public Token getToken() throws BadTokenException{
        if(buffer == null)
            return createToken(); //consume
        Token token = buffer;
        buffer = null; //got consumed
        return token;
    }

    public Token peek() throws BadTokenException{
        if(buffer == null)
            buffer = createToken();
        return buffer;
    }

    private Token createToken() throws BadTokenException {
        char c = stream.readCharacter();
        currentTokenLinePosition = stream.getCurrentLinePosition();
        currentTokenColumnPosition = stream.getCurrentColumnPosition();
        if(c == StreamHandler.EOF_CHARACTER) //no more stream
            return null;
        Token token;
        if(c == StreamHandler.EOL_CHARACTER) //explicit, because too important
            token = new Token(BlockTokenType.NEWLINE);
        else
            if((token = checkSingletonToken(c)) == null)
                if((token = checkSmallLetterToken(c)) == null)
                    if((token = checkLargeLetterToken(c)) == null)
                       if((token = checkZeroToken(c)) == null)
                            if((token = checkNumberToken(c)) == null)
                               if((token = checkSpecialCharactersToken(c)) == null)
                                    throw new BadTokenException(currentTokenLinePosition, currentTokenColumnPosition); //cannot match token
        token.setPosition(currentTokenLinePosition, currentTokenColumnPosition); //set here to reduce code
        return token;
    }

    private Token checkSmallLetterToken(char c){
        if (SMALL_LETTERS.indexOf(c) != -1){
            boolean possibleKeyword = true;
            String text = Character.toString(c);
            while(true){
                c = stream.readCharacter();
                if(SMALL_LETTERS.indexOf(c) == -1) //doesnt contain
                    if ((LARGE_LETTERS+NUMBERS).indexOf(c) == -1) //doesnt contain
                        break;
                    else
                        possibleKeyword = false;
                text += c;
            }
            TokenType tokenType = null;
            if (possibleKeyword)
                tokenType = wordsTokenTable.getOrDefault(text, null);
            stream.returnCharacter(c);
            if(tokenType == null)
                return new IdentifierToken(IdentifierTokenType.NAME, text);
            else
                return new Token(tokenType);

        }
        return null;
    }

    private Token checkLargeLetterToken(char c){
        if(LARGE_LETTERS.indexOf(c) != -1){
            String text = Character.toString(c);
            while((SMALL_LETTERS+LARGE_LETTERS+NUMBERS).indexOf(c = stream.readCharacter()) != -1)
                text += c;
            stream.returnCharacter(c);
            return new IdentifierToken(IdentifierTokenType.NAME, text);
        }
        return null;
    }

    private Token checkSingletonToken(char c){
        TokenType tokenType;
        if((tokenType = singletonTokenTable.getOrDefault(Character.toString(c), null)) != null){
            stream.prepareForNextToken();
            return new Token(tokenType);
        }
        return null;
    }

    private Token checkNumberToken(char c){
        if (NUMBERS_WITHOUT_ZERO.indexOf(c) != -1){
            String text = Character.toString(c);
            while(NUMBERS.indexOf(c = stream.readCharacter()) != -1) //integer
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
        return null;
    }

    private Token checkZeroToken(char c) throws BadTokenException {
        if(c == '0'){
            String text = Character.toString(c);
            c = stream.readCharacter();
            if(c == '.'){
                text += c;
                return processDouble(text);
            }
            if(NUMBERS.indexOf(c) != -1) //c is a number
                throw new BadTokenException(currentTokenLinePosition, currentTokenColumnPosition);
            stream.returnCharacter(c);
            return new IntegerNumberToken(0);
        }
        return null;
    }

    private Token checkSpecialCharactersToken(char c){
        if(CAN_HAVE_EQUALS_CHARACTERS.indexOf(c) != -1){
            TokenType tokenType;
            String text = Character.toString(c);
            c = stream.readCharacter();
            if(c == '*' || c == '/'){
                text = text + c;
                c = stream.readCharacter();
            }
            if(c == '=')
                text = text + c;
            else
                stream.returnCharacter(c);
            tokenType = ambiguousTokenTable.getOrDefault(text, null);
            stream.prepareForNextToken();
            if(tokenType != null)
                return new Token(tokenType);
            
        }
        return null;
    }

    private Token processDouble(String text){
        char c;
        while(NUMBERS.indexOf(c = stream.readCharacter()) != -1)
            text += c;
        stream.returnCharacter(c);
        return new DoubleNumberToken(Double.parseDouble(text));
    }

    public boolean isEOF(){
        return stream.isEOF();
    }
}