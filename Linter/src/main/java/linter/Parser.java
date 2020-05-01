package linter;

import linter.exception.BadSyntaxException;
import linter.syntax_tree.SyntaxTree;
import linter.token.Token;

public class Parser {
    private Lexer lexer;

    Parser(Lexer lexer){
        this.lexer = lexer;
    }

    SyntaxTree getNextSyntaxTree(){
        Token token = lexer.getToken();
        Token peek = lexer.peek();
        if(token == null) //EOF?
            return null;
        SyntaxTree syntaxTree = new SyntaxTree(); //has one production
        while(true){
            try {
                syntaxTree.improve(token, peek);
            }
            catch(BadSyntaxException exception){
                ErrorHandler.handleBadSyntaxException(exception);
            }
            if(syntaxTree.finished())
                break;
            token = lexer.getToken();
        }
        syntaxTree.reduce();
        return syntaxTree;
    }
}