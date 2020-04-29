package linter.syntax_tree;

import linter.exception.BadSyntaxException;
import linter.token.Token;
import linter.token.type.TokenType;

public class TokenTypeNode implements Node {
    TokenType tokenType;

    public TokenTypeNode(TokenType tokenType){
        this.tokenType = tokenType;
    }

    @Override
    public boolean processToken(Token token) throws BadSyntaxException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Node getNextNode() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getSubtreeSize() {
        // TODO Auto-generated method stub
        return 0;
    }

}