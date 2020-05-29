package linter.token.type;

import linter.syntax_tree.TreeElementVisitor;;

public enum NullTokenType implements TokenType {
    NULL;

    @Override
    public void accept(TreeElementVisitor visitor) {
        visitor.visit(this);
    }
}