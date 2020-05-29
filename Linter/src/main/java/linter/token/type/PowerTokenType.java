package linter.token.type;

import linter.syntax_tree.TreeElementVisitor;;

public enum PowerTokenType implements TokenType {
    POWER;

    @Override
    public void accept(TreeElementVisitor visitor){
        visitor.visit(this);
    }

}