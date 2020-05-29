package linter.token.type;

import linter.syntax_tree.TreeElementVisitor;;

public enum LogicTokenType implements TokenType {
    AND, OR, NOT, TRUE, FALSE;

    @Override
    public void accept(TreeElementVisitor visitor){
        visitor.visit(this);
    }

}