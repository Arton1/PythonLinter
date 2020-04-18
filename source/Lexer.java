import token.Token;
import token.type.*;
import java.util.HashMap;

public class Lexer {
    private StreamHandler stream;
    private HashMap<String, TokenType> tokenTable;

    Lexer(StreamHandler stream){
        this.stream = stream;
        createTokenTable();
    }

    void createTokenTable(){
        tokenTable.put("return", SimpleStatementTokenType.RETURN);
        tokenTable.put("in", CompareTokenType.IN);
        tokenTable.put("pass", SimpleStatementTokenType.PASS);
        tokenTable.put("break", SimpleStatementTokenType.BREAK);
        tokenTable.put("continue", SimpleStatementTokenType.CONTINUE);
        tokenTable.put("is", CompareTokenType.IS);
        tokenTable.put("import", SimpleStatementTokenType.IMPORT);
        tokenTable.put("else", CompoundStatementTokenType.RETURN);
        tokenTable.put("elif", CompoundStatementTokenType.RETURN);
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
        //need to add more 
    }

    public Token getToken(){
        Token token;
        int tokenLinePosition = stream.getCurrentLinePosition();
        int tokenColumnPosition = stream.getCurrentColumnPosition();
        token = createToken();
        if(token == null) //token doesnt exist, bad token
            ErrorHandler.handleBadTokenError();
        token.addPosition(tokenLinePosition, tokenColumnPosition);
        return token;
    }

    private Token createToken(){
        char c = stream.readCharacter();
        if ("abcdefghijklmopqrstuvw".indexOf(c) != -1){

        } 
        if ("ABCDEFGHIJKLMNOPQRSTUVW".indexOf(c) != -1){

        }
    }
}

