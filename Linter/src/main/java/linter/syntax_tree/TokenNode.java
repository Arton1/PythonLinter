package linter.syntax_tree;

import linter.token.Token;

public class TokenNode extends Node {

    Token token;

    protected TokenNode(Node parent) {
        super(parent);
        // TODO Auto-generated constructor stub
    }

    @Override
    boolean processToken(Token token) {
        // TODO Auto-generated method stub
        return false;
    }

    /*
    * Should cause error
    */
    @Override
    boolean changeState() {
        // TODO Auto-generated method stub
        return false;
    }
}