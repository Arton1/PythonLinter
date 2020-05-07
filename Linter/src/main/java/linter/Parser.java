package linter;

import linter.syntax_tree.SyntaxTree;
import linter.token.Token;
import linter.token.type.BlockTokenType;

public class Parser {
    private Lexer lexer;

    Parser(Lexer lexer){
        this.lexer = lexer;
    }

    SyntaxTree getNextSyntaxTree(){
        Token token = lexer.getToken();
        Token peek = lexer.peek();
        if(token.getTokenType() == BlockTokenType.EOF)
            return null;
        SyntaxTree syntaxTree = new SyntaxTree(); //has one production
        while(true){
            syntaxTree.improve(token, peek);
            if(syntaxTree.finished())
                break;
            token = lexer.getToken();
        }
        syntaxTree.reduce();
        return syntaxTree;
    }
}