package linter.syntax_tree;

import linter.exception.BadSyntaxException;
import linter.token.Token;
import linter.token.type.TokenType;

public class TokenTypeNode extends Node {
    TokenType tokenType;

    public TokenTypeNode(ProductionNode parent, TokenType tokenType){
        super(parent);
        this.tokenType = tokenType;
    }

    @Override
    public boolean processToken(Token token) throws BadSyntaxException {
        if(token.getTokenType() != tokenType)
            throw new BadSyntaxException();
        TokenNode node = new TokenNode(parent, token);
        parent.exchange(this, node);
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
    public String getInformation() {
        return "Token type node : " + tokenType;
    }

    @Override
    public void printInformations() {
        System.out.println(getInformation());
    }

}