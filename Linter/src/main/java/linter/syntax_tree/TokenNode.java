package linter.syntax_tree;

import linter.exception.BadSyntaxException;
import linter.token.Token;

public class TokenNode extends Node {

    Token token;

    public TokenNode(ProductionNode parent, Token token) {
        super(parent);
        this.token = token;
    }

    @Override
    public boolean processToken(Token token) throws BadSyntaxException {
        if(this.token.getTokenType() != token.getTokenType())
            throw new BadSyntaxException();
        return false;
    }

    @Override
    public Node getNextNode() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getSubtreeSize() {
        return 1;
    }

    @Override
    public boolean shouldRevert() {
        // TODO Auto-generated method stub
        return false;
    }

}