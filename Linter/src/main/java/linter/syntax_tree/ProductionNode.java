package linter.syntax_tree;

import linter.production.Production;
import linter.token.Token;

public class ProductionNode extends Node {
    Production production;

    protected ProductionNode(Node parent) {
        super(parent);
        // TODO Auto-generated constructor stub
    }

    @Override
    boolean processToken(Token token) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    boolean changeState() {
        // TODO Auto-generated method stub
        return false;
    }

}