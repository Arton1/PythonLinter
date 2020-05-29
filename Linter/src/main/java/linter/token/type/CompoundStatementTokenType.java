package linter.token.type;

import linter.syntax_tree.TreeElementVisitor;

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