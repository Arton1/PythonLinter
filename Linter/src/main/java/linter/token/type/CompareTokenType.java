package linter.token.type;

import linter.visitor.Visitor;

public enum CompareTokenType implements TokenType {
    LESS, MORE, LESS_EQUAL, MORE_EQUAL, EQUAL, 
    OTHER_THAN, IN, IS;

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

}