package linter.token.type;

import linter.syntax_tree.TreeElementVisitor;

public enum NumberTokenType implements TokenType{
    INTEGER, DOUBLE;

    @Override
    public void accept(TreeElementVisitor visitor){
        visitor.visit(this);
    }

}