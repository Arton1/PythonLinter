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
    public boolean processToken(Token token, Token peek) throws BadSyntaxException {
        if(this.token.getTokenType() != token.getTokenType())
            throw new BadSyntaxException();
        return false;
    }

    @Override
    public int getSubtreeSize() {
        return 1;
    }

    @Override
    public String getInformation() {
        return "Token node : " + token.getTokenType();
    }

    @Override
    public void printInformations() {
        System.out.println(getInformation());
    }

    @Override
    public Node getNextNode() {
        return null; //no children in here
    }

}