package linter.token.type;

import linter.visitor.Visitor;

public enum BracketTokenType implements TokenType{
    CURLY_BEGIN, CURLY_END, SQUARED_BEGIN, 
    SQUARED_END, ROUNDED_BEGIN, ROUNDED_END;

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

}