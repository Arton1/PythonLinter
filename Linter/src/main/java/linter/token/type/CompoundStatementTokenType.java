package linter.token.type;

import linter.visitor.TreeElementVisitor;

public enum CompoundStatementTokenType implements TokenType {
    IF, ELIF, ELSE, 
    WHILE, 
    FOR,
    FUN,
    CLASS;

    @Override
    public void accept(TreeElementVisitor visitor){
        visitor.visit(this);
    }

}