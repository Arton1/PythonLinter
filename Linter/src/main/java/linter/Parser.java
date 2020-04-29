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
        SyntaxTree syntaxTree = new SyntaxTree();
        Token token;
        while((token = lexer.getToken()) != null && !syntaxTree.finished()){ 
            try{
                syntaxTree.improve(token);
            }
            catch(BadSyntaxException exception){
                ErrorHandler.handleBadSyntaxException(exception);
            }
        }
        syntaxTree.reduce();
        return syntaxTree;
    }
}