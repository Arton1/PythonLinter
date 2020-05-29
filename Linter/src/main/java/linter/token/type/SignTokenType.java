package linter.token.type;

import linter.syntax_tree.TreeElementVisitor;

public enum SignTokenType implements TokenType {
    PLUS, MINUS, NEGATION;

    @Override
    public void accept(TreeElementVisitor visitor){
        visitor.visit(this);
    }

}