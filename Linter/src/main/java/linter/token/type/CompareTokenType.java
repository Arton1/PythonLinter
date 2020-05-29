package linter.token.type;

import linter.syntax_tree.TreeElementVisitor;

public enum CompareTokenType implements TokenType {
    LESS, MORE, LESS_EQUAL, MORE_EQUAL, EQUAL, 
    OTHER_THAN, IN, IS;

    @Override
    public void accept(TreeElementVisitor visitor){
        visitor.visit(this);
    }

}