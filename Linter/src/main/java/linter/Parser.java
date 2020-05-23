package linter;

import linter.exception.BadTokenException;
import linter.syntax_tree.SyntaxTree;
import linter.token.Token;
import linter.token.type.BlockTokenType;

public class Parser {
    private Lexer lexer;

    Parser(Lexer lexer){
        this.lexer = lexer;
    }

    SyntaxTree getNextSyntaxTree(){
        Token token = null, peek = null;
        try{
            token = lexer.getToken();
            peek = lexer.peek();
        }
        catch (BadTokenException e){
            ErrorHandler.handleBadTokenException(e);
        }
        if(token.getTokenType() == BlockTokenType.EOF)
            return null;
        SyntaxTree syntaxTree = new SyntaxTree(); //has one production
        while(true){
            syntaxTree.improve(token, peek);
            if(syntaxTree.finished())
                break;
            try {
                token = lexer.getToken();
                peek = lexer.peek();
            }
            catch (BadTokenException e){
                ErrorHandler.handleBadTokenException(e);
            }
        }
        syntaxTree.resetTravel();
        return syntaxTree;
    }
}