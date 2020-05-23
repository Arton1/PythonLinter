package linter;

import linter.exception.BadTokenException;
import linter.syntax_tree.SyntaxTree;
import linter.syntax_tree.SyntaxTreeBuilder;
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
        SyntaxTreeBuilder syntaxTreeBuilder = new SyntaxTreeBuilder(); //has one production
        while(true){
            syntaxTreeBuilder.improve(token, peek);
            if(syntaxTreeBuilder.finished())
                break;
            try {
                token = lexer.getToken();
                peek = lexer.peek();
            }
            catch (BadTokenException e){
                ErrorHandler.handleBadTokenException(e);
            }
        }
        syntaxTreeBuilder.resetTravel();
        return new SyntaxTree(syntaxTreeBuilder);
    }
}